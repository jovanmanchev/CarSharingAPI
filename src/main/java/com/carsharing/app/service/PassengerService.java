package com.carsharing.app.service;

import com.carsharing.app.dto.PassengerDto;
import com.carsharing.app.exceptions.PassengerNotFoundException;

public interface PassengerService {

    PassengerDto getPassengerById(Long id) throws PassengerNotFoundException;
}
