package com.carsharing.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestsForPassengerDto {

    List<RequestDto> requests;
}
