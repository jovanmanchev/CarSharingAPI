package com.carsharing.app.service;

import com.carsharing.app.dto.DriverDto;
import com.carsharing.app.dto.DriverRequestDto;
import com.carsharing.app.dto.PassengerDto;
import com.carsharing.app.dto.PassengerRequestDto;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.exceptions.PassengerNotFoundException;

public interface DriverService {

    DriverDto getDriverById(Long id) throws DriverNotFoundException;

    DriverDto updatePassenger(DriverRequestDto driverRequestDto, Long id) throws DriverNotFoundException;
}

