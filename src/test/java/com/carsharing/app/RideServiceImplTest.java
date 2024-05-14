package com.carsharing.app;

import com.carsharing.app.dto.RideCreationDto;
import com.carsharing.app.dto.RideResponseDto;
import com.carsharing.app.dto.RidesForDriverResponseDto;
import com.carsharing.app.enums.RideStatusEnum;
import com.carsharing.app.exceptions.DriverNotFoundException;
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
import static org.mockito.Mockito.*;

@SpringBootTest
public class RideServiceImplTest {
    @Mock
    private RideRepository rideRepository;
    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private RideServiceImpl rideService;

    private Ride ride;
    private Driver driver;
    private RideCreationDto rideDto;
    private Long validRideId = 1L;
    private Long validDriverId = 1L;
    @BeforeEach
    void setUp() {
        driver = new Driver();
        driver.setId(validDriverId);

        ride = new Ride();
        ride.setId(validRideId);
        ride.setDriver(driver);

        rideDto = new RideCreationDto();
        rideDto.setLocationFrom("New Location From");
        rideDto.setLocationTo("New Location To");
        rideDto.setTimeFrom(LocalDateTime.now().minusHours(1));
        rideDto.setTimeTo(LocalDateTime.now().plusHours(1));
        rideDto.setPricePerPerson(30);
        rideDto.setChattiness(true);
        rideDto.setPets(false);
        rideDto.setSmoking(true);
        rideDto.setMusic(false);
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
    @Test
    void updateRide_Success() {
        when(rideRepository.findById(validRideId)).thenReturn(Optional.of(ride));
        when(rideRepository.save(any(Ride.class))).thenReturn(ride);

        Ride updatedRide = rideService.updateRide(validRideId, rideDto, validDriverId);

        assertNotNull(updatedRide);
        assertEquals(rideDto.getLocationFrom(), updatedRide.getLocationFrom());
        assertEquals(rideDto.getLocationTo(), updatedRide.getLocationTo());
        assertEquals(rideDto.getPricePerPerson(), updatedRide.getPricePerPerson());
    }

    @Test
    void updateRide_RideNotFound() {
        when(rideRepository.findById(validRideId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> rideService.updateRide(validRideId, rideDto, validDriverId));
    }

    @Test
    void updateRide_UnauthorizedDriver() {
        when(rideRepository.findById(validRideId)).thenReturn(Optional.of(ride));
        Long unauthorizedDriverId = 2L;

        assertThrows(IllegalStateException.class, () -> rideService.updateRide(validRideId, rideDto, unauthorizedDriverId));
    }

    @Test
    void deleteRide_Success() {
        ride = new Ride();
        ride.setId(validRideId);
        when(rideRepository.findById(validRideId)).thenReturn(Optional.of(ride));
        doNothing().when(rideRepository).delete(ride);

        assertDoesNotThrow(() -> rideService.deleteRide(validRideId, validRideId));

        verify(rideRepository, times(1)).delete(ride);
    }

    @Test
    void deleteRide_RideNotFound() {
        when(rideRepository.findById(validRideId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> rideService.deleteRide(validRideId, validRideId));
    }

    @Test
    void searchRides_ReturnsMatchingRides() {

        String startLocation = "100 Main St";
        String destination = "200 Broadway";
        LocalDateTime departure = LocalDateTime.now().plusDays(1);

        Ride ride1 = createMockRide(1L, startLocation, destination, departure);
        Ride ride2 = createMockRide(2L, startLocation, destination, departure.plusHours(1));
        List<Ride> expectedRides = Arrays.asList(ride1, ride2);


        when(rideRepository.findAllByLocationFromAndLocationToAndTimeFromGreaterThanEqual(startLocation, destination, departure))
                .thenReturn(expectedRides);

        // Execution
        List<RideResponseDto> result = rideService.searchRides(startLocation, destination, departure);

        // Verification
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(expectedRides));
    }

    @Test
    void searchRides_ReturnsEmptyListWhenNoRidesMatch() {

        String startLocation = "300 Side St";
        String destination = "400 Other Ave";
        LocalDateTime departure = LocalDateTime.now().plusDays(2);


        when(rideRepository.findAllByLocationFromAndLocationToAndTimeFromGreaterThanEqual(startLocation, destination, departure))
                .thenReturn(Arrays.asList());

        // Execution
        List<RideResponseDto> result = rideService.searchRides(startLocation, destination, departure);

        // Verification
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void pastRidesForDriver_Success() {

        Long driverId = 1L;
        Driver driver = new Driver();
        driver.setId(driverId);

        Ride ride1 = createMockRide(1L, LocalDateTime.now().minusDays(10));
        Ride ride2 = createMockRide(2L, LocalDateTime.now().minusDays(5));
        List<Ride> rides = Arrays.asList(ride1, ride2);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(rideRepository.findAllByDriverAndTimeFromLessThan(eq(driver), any(LocalDateTime.class)))
                .thenReturn(rides);

        // Execution
        RidesForDriverResponseDto result = rideService.pastRidesForDriver(driverId);

        // Verification
        assertNotNull(result);
        assertEquals(2, result.ridesForDriver.size());
    }
    @Test
    void pastRidesForDriver_DriverNotFound() {

        Long driverId = 2L;

        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        // Execution & Verification
        assertThrows(DriverNotFoundException.class, () -> rideService.pastRidesForDriver(driverId));
    }

    @Test
    void incomingRidesForDriver_Success() {

        Long driverId = 1L;
        Driver driver = new Driver();
        driver.setId(driverId);

        Ride ride1 = createMockRide(1L, LocalDateTime.now().plusDays(1));
        Ride ride2 = createMockRide(2L, LocalDateTime.now().plusDays(2));
        List<Ride> rides = Arrays.asList(ride1, ride2);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(rideRepository.findAllByDriverAndTimeFromGreaterThanEqual(eq(driver), any(LocalDateTime.class)))
                .thenReturn(rides);

        // Execution
        RidesForDriverResponseDto result = rideService.upcomingRidesForDriver(driverId);

        // Verification
        assertNotNull(result);
        assertEquals(2, result.ridesForDriver.size());
    }

    @Test
    void incomingRidesForDriver_DriverNotFound() {

        Long driverId = 2L;

        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        // Execution & Verification
        assertThrows(DriverNotFoundException.class, () -> rideService.upcomingRidesForDriver(driverId));
    }

    private Ride createMockRide(Long rideId, Driver driver) {
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setDriver(driver);

        return ride;
    }
    private Ride createMockRide(Long rideId, String locationFrom, String locationTo, LocalDateTime timeFrom) {
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setLocationFrom(locationFrom);
        ride.setLocationTo(locationTo);
        ride.setTimeFrom(timeFrom);
        return ride;
    }
    private Ride createMockRide(Long rideId, LocalDateTime departureTime) {
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setTimeFrom(departureTime);
        return ride;
    }
}
