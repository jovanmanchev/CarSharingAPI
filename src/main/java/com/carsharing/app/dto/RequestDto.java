package com.carsharing.app.dto;

import com.carsharing.app.model.Passenger;
import com.carsharing.app.model.Ride;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {


    private float passengerRating;
    private String passengerPhoneNumber;
    private long rideId;

    public static RequestDto createRequestDto(Passenger passenger, Ride ride){
        RequestDto requestDto = new RequestDto();
        requestDto.passengerRating = passenger.getRating();
        requestDto.passengerPhoneNumber = passenger.getPhoneNumber();
        requestDto.rideId = ride.getId();

        return  requestDto;
    }

}
