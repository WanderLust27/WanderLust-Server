package com.wanderlust.wanderlust_app.CustomRole;


import com.wanderlust.wanderlust_app.user.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CustomRole extends User {
    private String roleName;
    @Override
    public String getUserRole(){
        return "CUSTOM_ROLE";
    }
}
