package com.flavorbooking.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountRegisterDTO {
    private String username;
    private String email;
    private String password;

    @JsonProperty("is_restaurant")
    private boolean isRestaurant; // set true at register restaurant api
}
