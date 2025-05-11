package com.wanderlust.wanderlust_app.businessOwner;

import com.wanderlust.wanderlust_app.businessOwner.enums.SubscriptionTier;
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
public class BusinessOwner extends User {

    private String businessLicense;
    private SubscriptionTier subscription;

    @Override
    public String getUserRole(){
        return "BUSINESS_OWNER";
    }
}
