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
@Entity
public class OrderCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty("full_name")
    private  String fullName;
    @JsonProperty("order_date")
    private LocalDate orderDate;
    private  String Phone;
    @JsonProperty("time_from")
    private LocalTime timeFrom;
    @JsonProperty("time_to")
    private  LocalTime timeTo;
    @JsonProperty("number_people")
    private  Integer numberPeople;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "restaurant_table_id")
    private RestaurantTable restaurantTable;



}
