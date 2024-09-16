package com.flavorbooking.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceResponse<T> {
    private boolean success;
    private String message;
    private T data;

}

