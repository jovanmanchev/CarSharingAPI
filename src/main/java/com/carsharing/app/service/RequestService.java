package com.carsharing.app.service;

import com.carsharing.app.dto.*;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.exceptions.PassengerNotFoundException;
import com.carsharing.app.exceptions.RideNotFoundException;
import com.carsharing.app.model.Request;

public interface RequestService {

    RequestCreateResponseDto makeRequest(RequestCreateDto requestCreateDto) throws PassengerNotFoundException, RideNotFoundException;
    RequestsForDriverDto getRequestsForDriver(Long driverId) throws DriverNotFoundException;

    RequestsForPassengerDto getRequestsForPassenger(Long passengerId) throws PassengerNotFoundException;

    RequestAcceptDto acceptRequest(Long requestId, Long driverId);

    RequestAcceptDto ignoreRequest(Long requestId, Long driverId);


}
