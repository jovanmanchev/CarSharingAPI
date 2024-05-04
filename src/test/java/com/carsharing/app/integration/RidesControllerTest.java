package com.carsharing.app.integration;


import com.carsharing.app.dto.RidesForDriverResponseDto;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.service.RideService;
import com.carsharing.app.web.RequestsController;
import com.carsharing.app.web.RidesController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RidesController.class)
public class RidesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RideService rideService;

    @Test
    public void getPastRidesForDriver_ReturnsRides() throws Exception {
        Long driverId = 1L;
        RidesForDriverResponseDto ridesDto = new RidesForDriverResponseDto();

        when(rideService.pastRidesForDriver(driverId)).thenReturn(ridesDto);

        mockMvc.perform(get("/api/rides/pastRidesForDriver/{driverId}", driverId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ridesForDriver").exists());
    }
    @Test
    public void getPastRidesForDriver_DriverNotFound() throws Exception {
        Long driverId = 1L;
        when(rideService.pastRidesForDriver(driverId)).thenThrow(new DriverNotFoundException("Driver not found"));

        mockMvc.perform(get("/api/rides/pastRidesForDriver/{driverId}", driverId))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("400 BAD_REQUEST \"Driver not found\"", result.getResolvedException().getMessage()));
    }

    @Test
    public void getIncomingRidesForDriver_ReturnsRides() throws Exception {
        Long driverId = 1L;
        RidesForDriverResponseDto ridesDto = new RidesForDriverResponseDto();

        when(rideService.incomingRidesForDriver(driverId)).thenReturn(ridesDto);

        mockMvc.perform(get("/api/rides/incomingRidesForDriver/{driverId}", driverId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ridesForDriver").exists());
    }
    @Test
    public void getIncomingRidesForDriver_DriverNotFound() throws Exception {
        Long driverId = 1L;
        when(rideService.incomingRidesForDriver(driverId)).thenThrow(new DriverNotFoundException("Driver not found"));

        mockMvc.perform(get("/api/rides/incomingRidesForDriver/{driverId}", driverId))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("400 BAD_REQUEST \"Driver not found\"", result.getResolvedException().getMessage()));
    }
}
