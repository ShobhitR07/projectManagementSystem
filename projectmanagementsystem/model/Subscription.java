package com.shobhit.projectmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private long id ;
    private LocalDateTime start ;
    private LocalDateTime end ;
    private PlanType planType ;
    private boolean isValid ;

    @OneToOne
    private User user ;


}
