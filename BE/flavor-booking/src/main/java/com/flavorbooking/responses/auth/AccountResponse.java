package com.flavorbooking.responses.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AccountResponse {
    private Integer id;
    private String username;
    private String email;

    @JsonProperty("is_active")
    private boolean isActive;
    private String provider;

    @JsonProperty("date_joined")
    private LocalDateTime dateJoined;
    private String avatar;
    private TokenResponse token;
    private String roleCode;

    @JsonProperty("restaurant_id")
    private Integer restaurantId;

}
