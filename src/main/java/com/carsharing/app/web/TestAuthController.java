package com.carsharing.app.web;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
public class TestAuthController {

    @GetMapping("/anon")
    public String anonEndPoint() {
        return "everyone can see this";
    }

    @GetMapping("/drivers")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public String driverEndPoint() {
        return "ONLY drivers can see this";
    }

    @GetMapping("/passenger")
    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    public String passengerEndPoint() {
        return "ONLY passenger can see this";
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminsEndPoint() {
        return "ONLY admins can see this";
    }
}