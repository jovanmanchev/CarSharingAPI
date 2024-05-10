package com.carsharing.app.service.impl;

import com.carsharing.app.dto.PassengerDto;
import com.carsharing.app.dto.PassengerRequestDto;
import com.carsharing.app.exceptions.PassengerNotFoundException;
import com.carsharing.app.model.Passenger;
import com.carsharing.app.repository.PassengerRepository;
import com.carsharing.app.service.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    @Override
    public PassengerDto getPassengerById(Long id) throws PassengerNotFoundException {
        Passenger passenger = this.passengerRepository.findById(id).orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        PassengerDto passengerDto = new PassengerDto();

        passengerDto.setId(passenger.getId());
        passengerDto.setAge(passenger.getUser().getAge());
        passengerDto.setRating(passenger.getRating());
        passengerDto.setPhoneNumber(passenger.getPhoneNumber());
        passengerDto.setFirstName(passenger.getUser().getFirstName());
        passengerDto.setLastName(passenger.getUser().getLastName());
        passengerDto.setBio(passenger.getBio());
        return passengerDto;
    }

    @Override
    public PassengerDto updatePassenger(PassengerRequestDto passengerRequestDto, Long id) throws PassengerNotFoundException {
        Passenger passenger = this.passengerRepository.findById(id).orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));
        passenger.setBio(passengerRequestDto.getBio());
        this.passengerRepository.save(passenger);
        PassengerDto passengerDto = new PassengerDto();

        passengerDto.setId(passenger.getId());
        passengerDto.setAge(passenger.getUser().getAge());
        passengerDto.setRating(passenger.getRating());
        passengerDto.setPhoneNumber(passenger.getPhoneNumber());
        passengerDto.setFirstName(passenger.getUser().getFirstName());
        passengerDto.setLastName(passenger.getUser().getLastName());
        passengerDto.setBio(passenger.getBio());
        return passengerDto;
    }
}
