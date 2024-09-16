package com.flavorbooking.responses.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
    private String refresh;
    private String access;
}
