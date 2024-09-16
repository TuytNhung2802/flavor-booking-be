package com.flavorbooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishDTO {
//    @JsonProperty("order_id")
//    @Min(value=1, message = "Order's ID must be > 0")
//    private Long orderId;
    private Integer id;
    private  String title;
    private String image;
    private  String description;
    private  Double price;
    @JsonProperty("old_price")
    private  Double oldPrice;
    @JsonProperty("product_status")
    private  String productStatus;
    private  Boolean featured;
    private LocalDate date;
    private  LocalDate updated;
    private Integer cid;
    private Integer rid;
}
