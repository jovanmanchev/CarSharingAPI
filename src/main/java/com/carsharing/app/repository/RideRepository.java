package com.carsharing.app.repository;

import com.carsharing.app.model.Driver;
import com.carsharing.app.model.Passenger;
import com.carsharing.app.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findAllByDriver(Driver driver);
    List<Ride> findByPassengersContains(Passenger passenger);

    List<Ride> findAllByLocationFromAndLocationToAndTimeFromGreaterThanEqual(String startLocation, String destination, LocalDateTime departure);

    List<Ride> findAllByDriverAndTimeFromLessThan(Driver driver, LocalDateTime timeFrom);

    List<Ride> findAllByDriverAndTimeFromGreaterThanEqual(Driver driver, LocalDateTime timeFrom);

    List<Ride> findAllByPassengersContainingAndTimeFromLessThan (Passenger passenger, LocalDateTime timeFrom);

    List<Ride> findAllByPassengersContainingAndTimeFromGreaterThanEqual (Passenger passenger, LocalDateTime timeFrom);
}
