package com.carsharing.app.repository;

import com.carsharing.app.model.Passenger;
import com.carsharing.app.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRide_Driver_Id(Long driverId);
}
