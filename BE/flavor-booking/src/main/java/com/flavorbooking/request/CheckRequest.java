package com.flavorbooking.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CheckRequest {
    private String tid;
    private String order_date;
    private String time_from, time_to;
}
