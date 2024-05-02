package com.carsharing.app;

import com.carsharing.app.dto.RideCreationDto;
import com.carsharing.app.enums.RideStatusEnum;
import com.carsharing.app.model.Driver;
import com.carsharing.app.model.Ride;
import com.carsharing.app.repository.DriverRepository;
import com.carsharing.app.repository.RideRepository;
import com.carsharing.app.service.impl.RideServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RideServiceImplTest {
    @Mock
    private RideRepository rideRepository;
    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private RideServiceImpl rideService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createRide_Success() {

        Long driverId = 1L;
        Driver driver = new Driver();
        driver.setId(driverId);

        RideCreationDto rideCreationDto = new RideCreationDto();
        rideCreationDto.setLocationFrom("100 Main St");
        rideCreationDto.setLocationTo("200 Broadway");
        rideCreationDto.setPricePerPerson(25);
        rideCreationDto.setTimeFrom(LocalDateTime.now());
        rideCreationDto.setTimeTo(LocalDateTime.now().plusHours(2));
        rideCreationDto.setChattiness(true);
        rideCreationDto.setPets(false);
        rideCreationDto.setSmoking(false);
        rideCreationDto.setMusic(true);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        Ride expectedRide = new Ride();
        expectedRide.setDriver(driver);
        expectedRide.setLocationFrom(rideCreationDto.getLocationFrom());
        expectedRide.setLocationTo(rideCreationDto.getLocationTo());
        expectedRide.setPricePerPerson(rideCreationDto.getPricePerPerson());
        expectedRide.setTimeFrom(rideCreationDto.getTimeFrom());
        expectedRide.setTimeTo(rideCreationDto.getTimeTo());
        expectedRide.setChattiness(rideCreationDto.isChattiness());
        expectedRide.setPets(rideCreationDto.isPets());
        expectedRide.setSmoking(rideCreationDto.isSmoking());
        expectedRide.setMusic(rideCreationDto.isMusic());
        expectedRide.setRideStatus(RideStatusEnum.CREATED);

        when(rideRepository.save(any(Ride.class))).thenReturn(expectedRide);


        Ride result = rideService.createRide(rideCreationDto, driverId);


        assertNotNull(result);
        assertEquals(expectedRide.getLocationFrom(), result.getLocationFrom());
        assertEquals(expectedRide.getLocationTo(), result.getLocationTo());
        assertEquals(expectedRide.getPricePerPerson(), result.getPricePerPerson());
        assertEquals(expectedRide.getRideStatus(), RideStatusEnum.CREATED);
    }

    @Test
    void findAllByDriver_DriverNotFound() {

        Long driverId = 2L;
        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class, () -> rideService.findAllByDriver(driverId));
    }

    @Test
    void findAllByDriver_Success() {

        Long driverId = 1L;
        Driver driver = new Driver();
        driver.setId(driverId);

        Ride ride1 = createMockRide(1L, driver);
        Ride ride2 = createMockRide(2L, driver);
        List<Ride> expectedRides = Arrays.asList(ride1, ride2);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(rideRepository.findAllByDriver(driver)).thenReturn(expectedRides);


        List<Ride> result = rideService.findAllByDriver(driverId);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(expectedRides));
    }

    private Ride createMockRide(Long rideId, Driver driver) {
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setDriver(driver);

        return ride;
    }

}
