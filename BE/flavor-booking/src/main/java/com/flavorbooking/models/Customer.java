//package com.flavorbooking.models;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Getter
//@Setter
//@Entity
//public class Customer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    @Column(nullable = false)
//    @JsonProperty("full_name")
//    private  String fullName;
//    private String phone;
//    @Column(unique = true,nullable = false)
//    private String email;
//    private String image;
//    private String provider;
//    private Integer verify;
//    @JsonProperty("password_token")
//    private String passwordToken;
//    private String address;
//
//    @OneToOne
//    @JoinColumn(name = "account_id")
//    private Account account;
//
//
//}
