package com.carsharing.app.service;

import com.carsharing.app.dto.PassengerDto;
import com.carsharing.app.dto.PassengerRequestDto;
import com.carsharing.app.exceptions.PassengerNotFoundException;

public interface PassengerService {

    PassengerDto getPassengerById(Long id) throws PassengerNotFoundException;

    PassengerDto updatePassenger(PassengerRequestDto passengerRequestDto, Long id) throws PassengerNotFoundException;
}
