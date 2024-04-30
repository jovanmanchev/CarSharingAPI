package com.carsharing.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private float rating;
    private String phoneNumber;
    private String bio;

    // preferences ?
    
    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;
}
