package com.carsharing.app.model;

import com.carsharing.app.enums.RideStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locationFrom;
    private String locationTo;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;
    private int pricePerPerson;

    @ManyToOne(fetch = FetchType.EAGER)
    private Driver driver;

    @ManyToMany
    @JoinTable(
            name = "ride_passengers",
            joinColumns = @JoinColumn(name = "ride_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
    private List<Passenger> passengers;

    private boolean chattiness;
    private boolean pets;
    private boolean smoking;
    private boolean music;

    @Enumerated(EnumType.STRING)
    private RideStatusEnum rideStatus;
}
