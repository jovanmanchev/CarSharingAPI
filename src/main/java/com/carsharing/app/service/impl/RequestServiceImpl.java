package com.carsharing.app.service.impl;

import com.carsharing.app.dto.RequestCreateDto;
import com.carsharing.app.dto.RequestCreateResponseDto;
import com.carsharing.app.dto.RequestDto;
import com.carsharing.app.dto.RequestsForDriverDto;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.exceptions.PassengerNotFoundException;
import com.carsharing.app.exceptions.RideNotFoundException;
import com.carsharing.app.model.Driver;
import com.carsharing.app.model.Passenger;
import com.carsharing.app.model.Request;
import com.carsharing.app.model.Ride;
import com.carsharing.app.repository.DriverRepository;
import com.carsharing.app.repository.PassengerRepository;
import com.carsharing.app.repository.RequestRepository;
import com.carsharing.app.repository.RideRepository;
import com.carsharing.app.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final PassengerRepository passengerRepository;
    private final RideRepository rideRepository;

    private final DriverRepository driverRepository;

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

        return new RequestCreateResponseDto(request.getId());


    }

    @Override
    public RequestsForDriverDto getRequestsForDriver(Long driverId) throws DriverNotFoundException {

        Driver driver = this.driverRepository.findById(driverId).orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        List<RequestDto> requestList = requestRepository.findAllByRide_Driver_Id(driverId)
                .stream()
                .map(request -> {
                    return RequestDto.createRequestDto(request.getPassenger(), request.getRide());
                })
                .collect(Collectors.toList());

        RequestsForDriverDto requestsForDriverDto = new RequestsForDriverDto();
        requestsForDriverDto.setRequests(requestList);
        return requestsForDriverDto;
    }
}
