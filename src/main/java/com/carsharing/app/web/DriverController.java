package com.carsharing.app.web;

import com.carsharing.app.dto.RideCreationDto;
import com.carsharing.app.dto.RideResponseDto;
import com.carsharing.app.dto.RidesForDriverResponseDto;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.model.Ride;
import com.carsharing.app.service.RideService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/drivers/")
@AllArgsConstructor
public class DriverController {
    private final RideService rideService;

    @GetMapping("/rides")
    public ResponseEntity<List<Ride>> getAllRidesForDriver(@PathVariable Long driverId){
        List<Ride> rides = rideService.findAllByDriver(driverId);
        return ResponseEntity.ok(rides);
    }

    @PostMapping("/createRide")
    public ResponseEntity<Ride> createRide(@RequestBody RideCreationDto rideDto, @PathVariable Long driverId) {
        Ride ride = rideService.createRide(rideDto, driverId);
        return new ResponseEntity<>(ride, HttpStatus.CREATED);
    }

    @PutMapping("/{rideId}")
    public ResponseEntity<Ride> updateRide(@PathVariable Long rideId, @RequestBody RideCreationDto rideDto, @PathVariable Long driverId) {
        Ride updatedRide = rideService.updateRide(rideId, rideDto, driverId);
        return ResponseEntity.ok(updatedRide);
    }

    @DeleteMapping("/{rideId}")
    public ResponseEntity<Ride> cancelRide(@PathVariable Long rideId, @PathVariable Long driverId){
        rideService.deleteRide(rideId,driverId);
        return ResponseEntity.noContent().build();
    }

    // see all requests from passengers

    // handle requests from passengers (add to ride or ignore)




}
