package com.flavorbooking.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flavorbooking.enums.RestaurantEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private  String email;
    private  String image;
    @Column(length = 1000)
    private  String description;
    private  String phone;
    private  String address;
    private Boolean isActive;
    @JsonProperty("time_open")
    private LocalTime timeOpen;
    @JsonProperty("time_close")
    private LocalTime timeClose;
    @JsonProperty("is_hot")
    private Integer isHot;
    private Long likes;

    @Enumerated(EnumType.STRING)
    private RestaurantEnum status;

    @OneToOne
    @JoinColumn(name = "account_id")
    private  Account account;
}
