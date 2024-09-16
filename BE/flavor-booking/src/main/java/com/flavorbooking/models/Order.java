package com.flavorbooking.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "orders")
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private  String fullName;
    private  String phone;
    private  Double price;
    private  Double oldPrice;
    @JsonProperty("product_status")
    private  Integer productStatus;
    @JsonProperty("time_from")
    private LocalTime timeFrom;
    @JsonProperty("time_to")
    private LocalTime timeTo;
    private LocalDate date;
    @JsonProperty("number_people")
    private  Integer numberPeople;
    private  Double deposited;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "restaurant_table_id")
    private RestaurantTable restaurantTable;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
