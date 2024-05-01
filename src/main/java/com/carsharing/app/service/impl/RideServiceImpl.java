package com.carsharing.app.service.impl;

import com.carsharing.app.dto.RideCreationDto;
import com.carsharing.app.enums.RideStatusEnum;
import com.carsharing.app.model.Driver;
import com.carsharing.app.model.Ride;
import com.carsharing.app.repository.DriverRepository;
import com.carsharing.app.repository.RideRepository;
import com.carsharing.app.service.RideService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    // Driver Functionalities - CRUD
    @Override
    public Ride createRide(RideCreationDto rideCreationDto, Long driverId) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));

        Ride ride = new Ride();
        ride.setDriver(driver);
        ride.setLocationFrom(rideCreationDto.getLocationFrom());
        ride.setLocationTo(rideCreationDto.getLocationTo());
        ride.setPricePerPerson(rideCreationDto.getPricePerPerson());
        ride.setTimeFrom(rideCreationDto.getTimeFrom());
        ride.setTimeTo(rideCreationDto.getTimeTo());
        ride.setChattiness(rideCreationDto.isChattiness());
        ride.setPets(rideCreationDto.isPets());
        ride.setSmoking(rideCreationDto.isSmoking());
        ride.setMusic(rideCreationDto.isMusic());
        ride.setRideStatus(RideStatusEnum.CREATED);

        return rideRepository.save(ride);
    }

    @Override
    public List<Ride> findAllByDriver(Long driverId) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));
        return rideRepository.findAllByDriver(driver);
    }

    @Override
    public Ride updateRide(Long rideId, RideCreationDto rideDto, Long driverId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!ride.getDriver().getId().equals(driverId)) {
            throw new IllegalStateException("Only the driver who created the ride can update it.");
        }

        if (rideDto.getLocationFrom() != null) ride.setLocationFrom(rideDto.getLocationFrom());
        if (rideDto.getLocationTo() != null) ride.setLocationTo(rideDto.getLocationTo());
        if (rideDto.getTimeFrom() != null) ride.setTimeFrom(rideDto.getTimeFrom());
        if (rideDto.getTimeTo() != null) ride.setTimeTo(rideDto.getTimeTo());
        ride.setPricePerPerson(rideDto.getPricePerPerson());
        ride.setChattiness(rideDto.isChattiness());
        ride.setPets(rideDto.isPets());
        ride.setSmoking(rideDto.isSmoking());
        ride.setMusic(rideDto.isMusic());

        return rideRepository.save(ride);
    }

    @Override
    public void deleteRide(Long rideId, Long driverId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));
        rideRepository.delete(ride);
    }

    // Passenger Functionalities
    @Override
    public List<Ride> searchRides(String startLocation, String destination, LocalDateTime departure) {
        return rideRepository.findAllByLocationFromAndLocationToAndTimeFromGreaterThanEqual(startLocation,destination,departure);
    }
}
