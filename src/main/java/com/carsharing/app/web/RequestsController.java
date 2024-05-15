package com.carsharing.app.web;


import com.carsharing.app.dto.*;
import com.carsharing.app.exceptions.DriverNotFoundException;
import com.carsharing.app.exceptions.PassengerNotFoundException;
import com.carsharing.app.exceptions.RideNotFoundException;
import com.carsharing.app.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/requests/")
@AllArgsConstructor
public class RequestsController {

    private final RequestService requestService;

    // see all requests from passengers
    @GetMapping("getRequestsForDriver/{driverId}")
    public ResponseEntity<?> getRequestsForDriver(@PathVariable Long driverId){
        try{
            RequestsForDriverDto requestsForDriverDto = this.requestService.getRequestsForDriver(driverId);
            return ResponseEntity.ok(requestsForDriverDto);
        }catch (DriverNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    // sending request to be added to a ride
    @PostMapping("requestRide")
    public ResponseEntity<?> requestForRide(@RequestBody RequestCreateDto requestCreateDto ) {
        try {
            RequestCreateResponseDto requestCreateResponseDto = this.requestService.makeRequest(requestCreateDto);
            return ResponseEntity.ok(requestCreateResponseDto);
        } catch (RideNotFoundException | PassengerNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }


    // handle requests from passengers (add to ride or ignore)
    @PostMapping("acceptRequest/{requestId}/{driverId}")
    public ResponseEntity<?> acceptRequest(@PathVariable Long requestId, @PathVariable Long driverId) {
        try {
            RequestAcceptDto requestAcceptDto = this.requestService.acceptRequest(requestId, driverId);
            return ResponseEntity.ok(requestAcceptDto);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @PostMapping("ignoreRequest/{requestId}/{driverId}")
    public ResponseEntity<?> ignoreRequest(@PathVariable Long requestId, @PathVariable Long driverId) {
        try {
            RequestAcceptDto requestAcceptDto = this.requestService.ignoreRequest(requestId, driverId);
            return ResponseEntity.ok(requestAcceptDto);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
