package com.carsharing.app.integration;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.carsharing.app.dto.RideCreationDto;
import com.carsharing.app.helper.Helper;
import com.carsharing.app.model.Ride;
import com.carsharing.app.service.RideService;
import com.carsharing.app.web.DriverController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RideService rideService;

    @Test
    public void getAllRidesForDriver_ReturnsRides() throws Exception {
        Long driverId = 1L;
        Ride ride = new Ride();
        List<Ride> rides = Arrays.asList(ride);

        when(rideService.findAllByDriver(driverId)).thenReturn(rides);

        mockMvc.perform(get("/api/drivers/rides/" + driverId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }
    @Test
    public void createRide_CreatesAndReturnsRide() throws Exception {
        Long driverId = 1L;
        Ride ride = new Ride();
        ride.setId(1L);
        RideCreationDto rideDto = new RideCreationDto();
        rideDto.setLocationFrom("Location 1");
        rideDto.setLocationTo("Location 2");
        rideDto.setTimeFrom(LocalDateTime.now());
        rideDto.setTimeFrom(LocalDateTime.now().plusHours(2));
        rideDto.setPricePerPerson(200);

        when(rideService.createRide(any(RideCreationDto.class), eq(driverId))).thenReturn(ride);

        mockMvc.perform(post("/api/drivers/createRide/" + driverId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Helper.asJsonString(rideDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ride.getId()));
    }
    @Test
    public void updateRide_UpdatesAndReturnsRide() throws Exception {
        Long rideId = 1L;
        Long driverId = 1L;
        Ride ride = new Ride();
        RideCreationDto rideDto = new RideCreationDto();

        when(rideService.updateRide(rideId, rideDto, driverId)).thenReturn(ride);

        mockMvc.perform(put("/api/drivers/{rideId}/{driverId}", rideId, driverId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Helper.asJsonString(rideDto)))

                .andExpect(status().isOk());
    }
    @Test
    public void cancelRide_DeletesRide() throws Exception {
        Long rideId = 1L;
        Long driverId = 1L;

        doNothing().when(rideService).deleteRide(rideId, driverId);

        mockMvc.perform(delete("/api/drivers/{rideId}/{driverId}", rideId, driverId))
                .andExpect(status().isNoContent());
    }


}
