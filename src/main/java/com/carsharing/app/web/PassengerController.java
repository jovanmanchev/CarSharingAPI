package com.carsharing.app.web;

import com.carsharing.app.model.Ride;
import com.carsharing.app.service.RideService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/passengers")
@AllArgsConstructor
public class PassengerController {
    private final RideService rideService;

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

    // sending request to be added to a ride

    // see all sent requests and their status


}
