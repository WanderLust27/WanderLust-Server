package com.wanderlust.wanderlust_app.auth;

import com.wanderlust.wanderlust_app.CustomRole.CustomRole;
import com.wanderlust.wanderlust_app.auth.DTOs.AuthResponseDTO;
import com.wanderlust.wanderlust_app.auth.DTOs.LoginDTO;
import com.wanderlust.wanderlust_app.auth.DTOs.RegisterDTO;
import com.wanderlust.wanderlust_app.auth.DTOs.TokensDTO;
import com.wanderlust.wanderlust_app.auth.interfaces.AuthService;
import com.wanderlust.wanderlust_app.businessOwner.BusinessOwner;
import com.wanderlust.wanderlust_app.businessOwner.enums.SubscriptionTier;
import com.wanderlust.wanderlust_app.user.User;
import com.wanderlust.wanderlust_app.user.persistence.UserDAO;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class AuthServiceImp implements AuthService {
    private final Keycloak keycloakAdminClient;
    private final UserDAO userDAO;
    private final String realm;
    private final String clientId;
    private final String clientSecret;
    private final String tokenUri;

    public AuthServiceImp(@Value("${KEYCLOAK_REALM}") String realm,
                          @Value("${KEYCLOAK_ADMIN}") String clientId,
                          @Value("${KEYCLOAK_ADMIN_PASSWORD}") String clientSecret ,
                          Keycloak keycloakAdminClient,
                          UserDAO userDAO ,
                          @Value("${KEYCLOAK_TOKEN_URI}") String tokenUri
                          ){
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.keycloakAdminClient = keycloakAdminClient;
        this.userDAO = userDAO;
        this.tokenUri = tokenUri;
    }

    private String extractUserId(Response response) {
        String location = response.getLocation().getPath();
        return location.substring(location.lastIndexOf('/') + 1);
    }


    @Override
    public AuthResponseDTO register(RegisterDTO registerDTO) {
        if (userDAO.existsByEmail(registerDTO.email())) {
            throw new RuntimeException("Email already exists");
        }
        if (userDAO.existsByPhoneNumber(registerDTO.phoneNumber())) {
            throw new RuntimeException("Phone Number already exists");
        }

        String keycloakUserId = createKeycloakUser(registerDTO);

        assignKeycloakRole(keycloakUserId, registerDTO.userType().name());

        User user = createAppUser(registerDTO, keycloakUserId);
        userDAO.save(user);

        TokensDTO tokens = getTokens(registerDTO.username(), registerDTO.password());

        return new AuthResponseDTO(user.getId(), tokens, user.getUserRole());
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        TokensDTO tokens = getTokens(loginDTO.identifier(), loginDTO.password());

        User user = userDAO.findByEmail(loginDTO.identifier())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setLastLogin(LocalDateTime.now());
        userDAO.save(user);

        return new AuthResponseDTO(user.getId(), tokens, user.getUserRole());
    }

    private TokensDTO getTokens(String username, String password) {
        return WebClient.create()
                .post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(
                        "client_id", clientId
                ))
                .retrieve()
                .bodyToMono(TokensDTO.class)
                .map(res -> new TokensDTO(res.accessToken(), res.refreshToken()))
                .block();
    }

    private String createKeycloakUser(RegisterDTO dto) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(dto.username());
        userRep.setEmail(dto.email());
        userRep.setFirstName(dto.firstName());
        userRep.setLastName(dto.familyName());
        userRep.setEnabled(true);
        userRep.setCredentials(List.of(createPasswordCredential(dto.password())));

        Response response = keycloakAdminClient.realm(realm).users().create(userRep);
        if (response.getStatus() != 201) {
            throw new RuntimeException("Keycloak user creation failed");
        }
        return extractUserId(response);
    }

    private void assignKeycloakRole(String userId, String roleName) {
        ClientRepresentation client = keycloakAdminClient.realm(realm)
                .clients().findByClientId(clientId).get(0);

        RoleRepresentation role = keycloakAdminClient.realm(realm)
                .clients().get(client.getId()).roles().get(roleName).toRepresentation();

        keycloakAdminClient.realm(realm).users().get(userId)
                .roles().clientLevel(client.getId()).add(List.of(role));
    }

    private User createAppUser(RegisterDTO dto, String keycloakId) {
        User user = switch (dto.userType().name().toUpperCase()) {
            case "BUSINESS_OWNER" -> {
                BusinessOwner bo = new BusinessOwner();
                bo.setBusinessLicense("PENDING");
                bo.setSubscription(SubscriptionTier.STANDARD);
                yield bo;
            }
            case "CUSTOM_ROLE" -> {
                CustomRole cr = new CustomRole();
                cr.setRoleName(dto.userType().name().toUpperCase());
                yield cr;
            }
            default -> throw new IllegalArgumentException("Invalid role: " + dto.userType().name());
        };

        user.setKeycloakId(keycloakId);
        user.setFirstName(dto.firstName());
        user.setFamilyName(dto.familyName());
        user.setEmail(dto.email());
        user.setPhoneNumber(dto.phoneNumber());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return user;
    }

    private CredentialRepresentation createPasswordCredential(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        return credential;
    }
}
