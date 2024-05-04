package com.carsharing.app.repository;

import com.carsharing.app.model.Passenger;
import com.carsharing.app.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRide_Driver_Id(Long driverId);
    List<Request> findRequestByPassengerId(Long passengerId);
}
