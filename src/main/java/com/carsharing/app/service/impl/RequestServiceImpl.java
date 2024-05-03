package com.carsharing.app.service.impl;

import com.carsharing.app.dto.*;
import com.carsharing.app.enums.RequestStatusEnum;
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
import java.util.Objects;
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
        request.setStatus(RequestStatusEnum.PENDING);
        request = requestRepository.save(request);

        return new RequestCreateResponseDto(request.getId());


    }

    @Override
    public RequestsForDriverDto getRequestsForDriver(Long driverId) throws DriverNotFoundException {

        Driver driver = this.driverRepository.findById(driverId).orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        List<RequestDto> requestList = requestRepository.findAllByRide_Driver_Id(driverId)
                .stream()
                .map(request -> {
                    return RequestDto.createRequestDto(request);
                })
                .collect(Collectors.toList());

        RequestsForDriverDto requestsForDriverDto = new RequestsForDriverDto();
        requestsForDriverDto.setRequests(requestList);
        return requestsForDriverDto;
    }

    @Override
    public RequestsForPassengerDto getRequestsForPassenger(Long passengerId) throws PassengerNotFoundException {
        Passenger passenger = this.passengerRepository.findById(passengerId).orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        List<RequestDto> requestList = requestRepository.findRequestByPassengerId(passengerId)
                .stream()
                .map(RequestDto::createRequestDto)
                .collect(Collectors.toList());

        RequestsForPassengerDto requestsForPassengerDto = new RequestsForPassengerDto();
        requestsForPassengerDto.setRequests(requestList);
        return requestsForPassengerDto;
    }

    @Override
    public RequestAcceptDto acceptRequest(Long requestId, Long driverId) {


        Request request = this.requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        if(!Objects.equals(request.getRide().getDriver().getId(), driverId))
           throw  new RuntimeException("The request is not for this driver");

        if(request.getStatus().equals(RequestStatusEnum.IGNORED))
            throw  new RuntimeException("The request is already ignored");
        Ride ride = request.getRide();

        List<Passenger> passengers = ride.getPassengers();
        passengers.add(request.getPassenger());
        request.setStatus(RequestStatusEnum.ACCEPTED);
        this.rideRepository.save(ride);



        return new RequestAcceptDto("Succesfully accepted");
    }

    @Override
    public RequestAcceptDto ignoreRequest(Long requestId, Long driverId) {
        Request request = this.requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));

        if(!Objects.equals(request.getRide().getDriver().getId(), driverId))
            throw  new RuntimeException("The request is not for this driver");

        if(request.getStatus().equals(RequestStatusEnum.ACCEPTED))
            throw  new RuntimeException("The request is already accepted");

        Ride ride = request.getRide();

        List<Passenger> passengers = ride.getPassengers();
        passengers.add(request.getPassenger());
        request.setStatus(RequestStatusEnum.IGNORED);
        this.rideRepository.save(ride);



        return new RequestAcceptDto("Succesfully ignored");
    }


}
