package com.carsharing.app.web;

import com.carsharing.app.dto.*;
import com.carsharing.app.exceptions.PassengerNotFoundException;
import com.carsharing.app.model.Ride;
import com.carsharing.app.service.PassengerService;
import com.carsharing.app.service.RequestService;
import com.carsharing.app.service.RideService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/passengers")
@AllArgsConstructor
public class PassengerController {
    private final RideService rideService;

    private final RequestService requestService;

    private final PassengerService passengerService;

    @GetMapping("/search")
    public ResponseEntity<List<Ride>> searchRides(
            @RequestParam String startLocation,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departure) {

        List<Ride> rides = rideService.searchRides(startLocation, destination, departure);
        return ResponseEntity.ok(rides);
    }

    // see all past rides

    // see all future rides


    // see all sent requests and their status
    @GetMapping("/requests/{pasengerId}")
    public ResponseEntity<?> getRequestsForPassenger(@PathVariable Long pasengerId){
        try{
            RequestsForPassengerDto requestsForPassengerDto = this.requestService.getRequestsForPassenger(pasengerId);
            return ResponseEntity.ok(requestsForPassengerDto);
        }catch (PassengerNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<?> getPassenger(@PathVariable Long passengerId){
        try{
            PassengerDto passengerDto = this.passengerService.getPassengerById(passengerId);
            return ResponseEntity.ok(passengerDto);
        }catch (PassengerNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
    @PostMapping("/update/{passengerId}")
    public ResponseEntity<?> updatePassenger(@RequestBody PassengerRequestDto passengerRequestDto, @PathVariable Long passengerId) {
        try{
            PassengerDto passengerDto = this.passengerService.updatePassenger(passengerRequestDto, passengerId);
            return new ResponseEntity<>(passengerDto, HttpStatus.CREATED);
        }

        catch (PassengerNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

}
