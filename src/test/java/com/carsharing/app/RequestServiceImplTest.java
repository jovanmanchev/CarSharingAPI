package com.carsharing.app;

import com.carsharing.app.dto.RequestCreateDto;
import com.carsharing.app.dto.RequestCreateResponseDto;
import com.carsharing.app.dto.RequestsForDriverDto;
import com.carsharing.app.enums.RideStatusEnum;
import com.carsharing.app.enums.UserTypeEnum;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.exceptions.PassengerNotFoundException;
import com.carsharing.app.exceptions.RideNotFoundException;
import com.carsharing.app.model.*;
import com.carsharing.app.repository.DriverRepository;
import com.carsharing.app.repository.PassengerRepository;
import com.carsharing.app.repository.RequestRepository;
import com.carsharing.app.repository.RideRepository;
import com.carsharing.app.service.impl.RequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private RideRepository rideRepository;
    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private RequestServiceImpl requestService;

    private RequestCreateDto requestCreateDto;
    private Passenger passenger;
    private Ride ride;

    private Request request;

    @BeforeEach
    void setUp() {

        passenger = new Passenger();
        passenger.setId(1L);
        passenger.setRating(4.5f);
        passenger.setPhoneNumber("123-456-7890");

        User passengerUser = new User();
        passengerUser.setId(1L);
        passengerUser.setFirstName("John");
        passengerUser.setLastName("Doe");
        passengerUser.setEmail("john.doe@example.com");
        passengerUser.setEncryptedPassword("encrypted_password");
        passengerUser.setType(UserTypeEnum.PASSENGER);
        passenger.setUser(passengerUser);


        ride = new Ride();
        ride.setPassengers(new ArrayList<>());
        ride.setId(1L);
        ride.setLocationFrom("100 Main St");
        ride.setLocationTo("200 Broadway");
        ride.setTimeFrom(LocalDateTime.of(2023, 5, 15, 8, 0));
        ride.setTimeTo(LocalDateTime.of(2023, 5, 15, 9, 0));
        ride.setPricePerPerson(20); // Sample price
        ride.setChattiness(true);
        ride.setPets(false);
        ride.setSmoking(false);
        ride.setMusic(true);


        Driver rideDriver = new Driver();
        rideDriver.setId(1L);
        rideDriver.setRating(4.8f);
        rideDriver.setPhoneNumber("987-654-3210");
        rideDriver.setBio("Experienced driver with over 10 years on the road.");

        User driverUser = new User();
        driverUser.setId(2L);
        driverUser.setFirstName("Alice");
        driverUser.setLastName("Smith");
        driverUser.setEmail("alice.smith@example.com");
        driverUser.setEncryptedPassword("another_encrypted_password");
        driverUser.setType(UserTypeEnum.DRIVER);
        rideDriver.setUser(driverUser);

        ride.setDriver(rideDriver);

        requestCreateDto = new RequestCreateDto();
        requestCreateDto.setPassengerId(passenger.getId());
        requestCreateDto.setRideId(ride.getId());

        request = new Request();
        request.setPassenger(passenger);
        request.setRide(ride);

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        when(rideRepository.findById(1L)).thenReturn(Optional.of(ride));
    }


    @Test
    void makeRequest_Success() throws Exception {
        Request savedRequest = new Request();
        savedRequest.setId(1L);
        savedRequest.setPassenger(passenger);
        savedRequest.setRide(ride);
        when(requestRepository.save(Mockito.<Request>any())).thenReturn(savedRequest);


        RequestCreateResponseDto response = requestService.makeRequest(requestCreateDto);
        assertNotNull(response);
        assertEquals(savedRequest.getId(), response.getId());
    }
    @Test
    void makeRequest_PassengerNotFound() {
        when(passengerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(PassengerNotFoundException.class, () -> {
            requestService.makeRequest(requestCreateDto);
        });
    }

    @Test
    void makeRequest_RideNotFound() {
        when(rideRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RideNotFoundException.class, () -> {
            requestService.makeRequest(requestCreateDto);
        });
    }

    @Test
    void getRequestsForDriver_Success() throws DriverNotFoundException {

        Long driverId = 1L;
        Driver driver = new Driver();
        driver.setId(driverId);
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));


        List<Request> requests = Arrays.asList(request);

        when(requestRepository.findAllByRide_Driver_Id(driverId)).thenReturn(requests);


        RequestsForDriverDto result = requestService.getRequestsForDriver(driverId);


        assertNotNull(result);
        assertEquals(1, result.getRequests().size());

    }
    @Test
    void getRequestsForDriver_DriverNotFound() {
        Long driverId = 2L;
        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());


        assertThrows(DriverNotFoundException.class, () -> {
            requestService.getRequestsForDriver(driverId);
        });
    }
}
