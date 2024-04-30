package com.carsharing.app.model;

import com.carsharing.app.enums.CarLuggageEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "car")
    private Driver driver;

    private String model;
    private int passengerCapacity;
    @Enumerated(EnumType.STRING)
    private CarLuggageEnum luggagePerPerson;
}
