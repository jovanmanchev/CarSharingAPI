package com.carsharing.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RideCreationDto {
    private String locationFrom;
    private String locationTo;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;
    private int pricePerPerson;
    private boolean chattiness;
    private boolean pets;
    private boolean smoking;
    private boolean music;
}
