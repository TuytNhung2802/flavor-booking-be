package com.flavorbooking.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String body;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "send_id")
    private Account sendId;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Account recipientId;

//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;

}
