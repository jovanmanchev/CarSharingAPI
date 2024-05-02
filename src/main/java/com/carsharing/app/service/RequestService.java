package com.carsharing.app.service;

import com.carsharing.app.dto.RequestCreateDto;
import com.carsharing.app.dto.RequestCreateResponseDto;
import com.carsharing.app.dto.RequestsForDriverDto;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.exceptions.PassengerNotFoundException;
import com.carsharing.app.exceptions.RideNotFoundException;
import com.carsharing.app.model.Request;

public interface RequestService {

    RequestCreateResponseDto makeRequest(RequestCreateDto requestCreateDto) throws PassengerNotFoundException, RideNotFoundException;
    RequestsForDriverDto getRequestsForDriver(Long driverId) throws DriverNotFoundException;
}
