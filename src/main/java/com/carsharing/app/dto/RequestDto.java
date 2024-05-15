package com.carsharing.app.dto;

import com.carsharing.app.enums.RequestStatusEnum;
import com.carsharing.app.model.Passenger;
import com.carsharing.app.model.Request;
import com.carsharing.app.model.Ride;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestDto {


    private float passengerRating;
    private String passengerPhoneNumber;
    private long rideId;

    private String locationFrom;
    private String locationTo;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;
    private Long requestId;

    private Long driverId;

    private RequestStatusEnum requestStatusEnum;
    public static RequestDto createRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.requestId = request.getId();
        requestDto.passengerRating = request.getPassenger().getRating();
        requestDto.passengerPhoneNumber = request.getPassenger().getPhoneNumber();
        requestDto.rideId = request.getRide().getId();
        requestDto.requestStatusEnum = request.getStatus();
        requestDto.locationFrom = request.getRide().getLocationFrom();
        requestDto.locationTo = request.getRide().getLocationTo();
        requestDto.timeFrom = request.getRide().getTimeFrom();
        requestDto.timeTo = request.getRide().getTimeTo();
        requestDto.driverId = request.getRide().getDriver().getId();
        return  requestDto;
    }

}
