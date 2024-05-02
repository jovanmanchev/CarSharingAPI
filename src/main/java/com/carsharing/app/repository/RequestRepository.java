package com.carsharing.app.repository;

import com.carsharing.app.model.Passenger;
import com.carsharing.app.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
