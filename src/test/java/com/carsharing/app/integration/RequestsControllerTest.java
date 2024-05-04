package com.carsharing.app.integration;

import static com.carsharing.app.helper.Helper.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.carsharing.app.dto.*;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.exceptions.RideNotFoundException;
import com.carsharing.app.helper.Helper;
import com.carsharing.app.model.Driver;
import com.carsharing.app.model.Passenger;
import com.carsharing.app.model.Request;
import com.carsharing.app.model.Ride;
import com.carsharing.app.service.RequestService;
import com.carsharing.app.service.RideService;
import com.carsharing.app.web.DriverController;
import com.carsharing.app.web.RequestsController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@WebMvcTest(RequestsController.class)
public class RequestsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestService requestService;



    @Test
    public void getRequestsForDriver_ReturnsRequests() throws Exception {
        Long driverId = 1L;


        RequestDto requestDto = new RequestDto();
        requestDto.setRideId(1L);
        RequestsForDriverDto dto = new RequestsForDriverDto();
        List<RequestDto> requestDtos = new ArrayList<>();
        requestDtos.add(requestDto);
        dto.setRequests(requestDtos);



        when(requestService.getRequestsForDriver(driverId)).thenReturn(dto);

        mockMvc.perform(get("/api/requests/getRequestsForDriver/{driverId}", driverId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requests").exists());
    }

    @Test
    public void getRequestsForDriver_DriverNotFound() throws Exception {
        Long driverId = 1L;
        when(requestService.getRequestsForDriver(driverId)).thenThrow(new DriverNotFoundException("Driver not found"));

        mockMvc.perform(get("/api/requests/getRequestsForDriver/{driverId}", driverId))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("400 BAD_REQUEST \"Driver not found\"", result.getResolvedException().getMessage()));
    }
    @Test
    public void requestForRide_Success() throws Exception {
        RequestCreateDto requestCreateDto = new RequestCreateDto();
        RequestCreateResponseDto requestCreateResponseDto = new RequestCreateResponseDto(1L);

        when(requestService.makeRequest(any(RequestCreateDto.class))).thenReturn(requestCreateResponseDto);

        mockMvc.perform(post("/api/requests/requestRide")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void requestForRide_RideNotFound() throws Exception {
        RequestCreateDto requestCreateDto = new RequestCreateDto();

        when(requestService.makeRequest(any(RequestCreateDto.class))).thenThrow(new RideNotFoundException("Ride not found"));

        mockMvc.perform(post("/api/requests/requestRide")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Helper.asJsonString(requestCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("400 BAD_REQUEST \"Ride not found\"", result.getResolvedException().getMessage()));
    }
}
