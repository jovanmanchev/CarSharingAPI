package com.carsharing.app.service;

import com.carsharing.app.dto.RideCreationDto;
import com.carsharing.app.model.Ride;

import java.time.LocalDateTime;
import java.util.List;

public interface RideService {
    public Ride createRide(RideCreationDto rideCreationDto, Long driverId);

    public List<Ride> findAllByDriver(Long driverId);

    public Ride updateRide(Long rideId, RideCreationDto rideDto, Long driverId);

    public void deleteRide(Long rideId, Long driverId);

    public List<Ride> searchRides(String startLocation, String destination, LocalDateTime departure);

}
