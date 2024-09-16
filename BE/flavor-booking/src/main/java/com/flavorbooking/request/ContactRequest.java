package com.flavorbooking.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequest {
    private String email, full_name, subject, message;
}
