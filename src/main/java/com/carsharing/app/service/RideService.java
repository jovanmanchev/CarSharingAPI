package com.carsharing.app.service;

import com.carsharing.app.dto.RideCreationDto;
import com.carsharing.app.dto.RideResponseDto;
import com.carsharing.app.dto.RidesForDriverResponseDto;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.exceptions.PassengerNotFoundException;
import com.carsharing.app.model.Ride;

import java.time.LocalDateTime;
import java.util.List;

public interface RideService {
    public Ride createRide(RideCreationDto rideCreationDto, Long driverId);

    public List<Ride> findAllByDriver(Long driverId);

    public Ride updateRide(Long rideId, RideCreationDto rideDto, Long driverId);

    public void deleteRide(Long rideId, Long driverId);

    public List<RideResponseDto> searchRides(String startLocation, String destination, LocalDateTime departure);

    RidesForDriverResponseDto pastRidesForDriver(Long driverId) throws DriverNotFoundException;

    List<RideResponseDto> upcomingRidesForPassenger(Long passengerId) throws PassengerNotFoundException;

    List<RideResponseDto> pastRidesForPassenger(Long passengerId) throws PassengerNotFoundException;

    RidesForDriverResponseDto upcomingRidesForDriver(Long driverId) throws DriverNotFoundException;
    List<RideResponseDto> getAllRides();

    RideResponseDto getDetailsForRide(Long id);

}
