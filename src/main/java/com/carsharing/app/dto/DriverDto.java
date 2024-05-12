package com.carsharing.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDto {

    private long id;
    private float rating;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private int age;
    private String bio;
}
