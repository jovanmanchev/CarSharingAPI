package com.carsharing.app.web;

import com.carsharing.app.dto.*;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.exceptions.PassengerNotFoundException;
import com.carsharing.app.model.Ride;
import com.carsharing.app.service.DriverService;
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
    private  final DriverService driverService;

    @GetMapping("/rides/{driverId}")
    public ResponseEntity<List<Ride>> getAllRidesForDriver(@PathVariable Long driverId){
        List<Ride> rides = rideService.findAllByDriver(driverId);
        return ResponseEntity.ok(rides);
    }

    @PostMapping("/createRide/{driverId}")
    public ResponseEntity<Ride> createRide(@RequestBody RideCreationDto rideDto, @PathVariable Long driverId) {
        try{
            Ride ride = rideService.createRide(rideDto, driverId);
            return new ResponseEntity<>(ride, HttpStatus.CREATED);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }

    }

    @PutMapping("/{rideId}/{driverId}")
    public ResponseEntity<Ride> updateRide(@PathVariable Long rideId, @RequestBody RideCreationDto rideDto, @PathVariable Long driverId) {
        Ride updatedRide = rideService.updateRide(rideId, rideDto, driverId);
        return ResponseEntity.ok(updatedRide);
    }

    @DeleteMapping("/{rideId}/{driverId}")
    public ResponseEntity<Ride> cancelRide(@PathVariable Long rideId, @PathVariable Long driverId){
        rideService.deleteRide(rideId,driverId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/driver/{driverId}")
    public ResponseEntity<?> getPassenger(@PathVariable Long driverId){
        try{
            DriverDto driverdto = this.driverService.getDriverById(driverId);
            return ResponseEntity.ok(driverdto);
        }catch (DriverNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
    @PostMapping("/update/{driverId}")
    public ResponseEntity<?> updatePassenger(@RequestBody DriverRequestDto driverRequestDto, @PathVariable Long driverId) {
        try{
            DriverDto driverdto = this.driverService.updatePassenger(driverRequestDto, driverId);
            return new ResponseEntity<>(driverdto, HttpStatus.CREATED);
        }

        catch (DriverNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }


}
