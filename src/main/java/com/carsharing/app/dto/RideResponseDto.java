package com.carsharing.app.dto;

import com.carsharing.app.enums.RideStatusEnum;
import com.carsharing.app.model.Driver;
import com.carsharing.app.model.Passenger;
import com.carsharing.app.model.Ride;

import java.time.LocalDateTime;
import java.util.List;

public class RideResponseDto {

    private Long id;

    private String locationFrom;
    private String locationTo;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;
    private int pricePerPerson;

    private Driver driver;
    private List<Passenger> passengers;

    private boolean chattiness;
    private boolean pets;
    private boolean smoking;
    private boolean music;

    private RideStatusEnum rideStatus;

    public static RideResponseDto createRideResponseDto(Ride ride){
        RideResponseDto rideResponseDto = new RideResponseDto();

        rideResponseDto.id = ride.getId();
        rideResponseDto.locationFrom = ride.getLocationFrom();
        rideResponseDto.locationTo = ride.getLocationTo();
        rideResponseDto.timeFrom = ride.getTimeFrom();
        rideResponseDto.timeTo = ride.getTimeTo();
        rideResponseDto.pricePerPerson = ride.getPricePerPerson();
        rideResponseDto.driver = ride.getDriver();
        rideResponseDto.passengers = ride.getPassengers();
        rideResponseDto.chattiness = ride.isChattiness();
        rideResponseDto.pets = ride.isPets();
        rideResponseDto.smoking = ride.isSmoking();
        rideResponseDto.music = ride.isMusic();
        rideResponseDto.rideStatus = ride.getRideStatus();

        return rideResponseDto;

    }
}
