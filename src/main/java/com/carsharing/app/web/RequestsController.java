package com.carsharing.app.web;


import com.carsharing.app.dto.RequestCreateDto;
import com.carsharing.app.dto.RequestCreateResponseDto;
import com.carsharing.app.dto.RideCreationDto;
import com.carsharing.app.dto.RidesForDriverResponseDto;
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

    // handle requests from passengers (add to ride or ignore)
    @GetMapping("requestRide")
    public ResponseEntity<?> requestForRide(@RequestBody RequestCreateDto requestCreateDto ) {
        try {
            RequestCreateResponseDto requestCreateResponseDto = this.requestService.makeRequest(requestCreateDto);
            return ResponseEntity.ok(requestCreateResponseDto);
        } catch (RideNotFoundException | PassengerNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
