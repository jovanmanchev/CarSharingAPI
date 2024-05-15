package com.carsharing.app.service.impl;


import com.carsharing.app.dto.DriverDto;
import com.carsharing.app.dto.DriverRequestDto;
import com.carsharing.app.dto.PassengerDto;
import com.carsharing.app.dto.PassengerRequestDto;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.exceptions.PassengerNotFoundException;
import com.carsharing.app.model.Driver;
import com.carsharing.app.model.Passenger;
import com.carsharing.app.repository.DriverRepository;
import com.carsharing.app.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    @Override
    public DriverDto getDriverById(Long id) throws DriverNotFoundException {
        Driver driver = this.driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException("Passenger not found"));

        DriverDto driverDto = new DriverDto();

        driverDto.setId(driver.getId());
        driverDto.setAge(driver.getUser().getAge());
        driverDto.setRating(driver.getRating());
        driverDto.setPhoneNumber(driver.getPhoneNumber());
        driverDto.setFirstName(driver.getUser().getFirstName());
        driverDto.setLastName(driver.getUser().getLastName());
        driverDto.setBio(driver.getBio());
        return driverDto;
    }

    @Override
    public DriverDto updatePassenger(DriverRequestDto driverRequestDto, Long id) throws DriverNotFoundException {
        Driver driver = this.driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException("Passenger not found"));
        driver.setBio(driverRequestDto.getBio());
        this.driverRepository.save(driver);
        DriverDto driverDto = new DriverDto();

        driverDto.setId(driver.getId());
        driverDto.setAge(driver.getUser().getAge());
        driverDto.setRating(driver.getRating());
        driverDto.setPhoneNumber(driver.getPhoneNumber());
        driverDto.setFirstName(driver.getUser().getFirstName());
        driverDto.setLastName(driver.getUser().getLastName());
        driverDto.setBio(driver.getBio());
        return driverDto;
    }
}
