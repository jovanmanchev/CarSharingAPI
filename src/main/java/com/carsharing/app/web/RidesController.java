package com.carsharing.app.web;

import com.carsharing.app.dto.RidesForDriverResponseDto;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.service.RideService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("api/rides/")
@AllArgsConstructor
public class RidesController {
    private final RideService rideService;

    // past rides and current rides different endpoints

    @GetMapping("pastRidesForDriver/{driverId}")
    public ResponseEntity<?> getPastRidesForDriver(@PathVariable Long driverId) {
        try {
            RidesForDriverResponseDto ridesDto = rideService.pastRidesForDriver(driverId);
            return ResponseEntity.ok(ridesDto);
        } catch (DriverNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
    @GetMapping("incomingRidesForDriver/{driverId}")
    public ResponseEntity<?> getIncomingRidesForDriver(@PathVariable Long driverId) {
        try {
            RidesForDriverResponseDto ridesDto = rideService.pastRidesForDriver(driverId);
            return ResponseEntity.ok(ridesDto);
        } catch (DriverNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
