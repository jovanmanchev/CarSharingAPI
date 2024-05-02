package com.carsharing.app.service.impl;

import com.carsharing.app.dto.RequestCreateDto;
import com.carsharing.app.dto.RequestCreateResponseDto;
import com.carsharing.app.exceptions.PassengerNotFoundException;
import com.carsharing.app.exceptions.RideNotFoundException;
import com.carsharing.app.model.Passenger;
import com.carsharing.app.model.Request;
import com.carsharing.app.model.Ride;
import com.carsharing.app.repository.PassengerRepository;
import com.carsharing.app.repository.RequestRepository;
import com.carsharing.app.repository.RideRepository;
import com.carsharing.app.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final PassengerRepository passengerRepository;
    private final RideRepository rideRepository;

    @Override
    public RequestCreateResponseDto makeRequest(RequestCreateDto requestCreateDto) throws PassengerNotFoundException, RideNotFoundException{

        Passenger passenger = this.passengerRepository.findById(requestCreateDto.getPassengerId())
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        Ride ride = this.rideRepository.findById(requestCreateDto.getRideId())
                .orElseThrow(() -> new RideNotFoundException("Ride not found"));

        Request request = new Request();
        request.setRide(ride);
        request.setPassenger(passenger);

        request = requestRepository.save(request);
        List<Passenger> passengerList = ride.getPassengers();
        passengerList.add(passenger);

        rideRepository.save(ride);

        return new RequestCreateResponseDto("Request succesfully created");


    }
}
