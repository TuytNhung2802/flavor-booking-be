package com.flavorbooking.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private Integer id;
    private String email;
    private String userName;
    private String fullName;
    private Boolean active;
    private String address;
    private String phoneNumber;
    private String provider;
}
