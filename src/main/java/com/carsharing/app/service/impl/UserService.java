package com.carsharing.app.service.impl;


import com.carsharing.app.enums.UserTypeEnum;
import com.carsharing.app.model.Driver;
import com.carsharing.app.model.Passenger;
import com.carsharing.app.model.User;
import com.carsharing.app.repository.DriverRepository;
import com.carsharing.app.repository.PassengerRepository;
import com.carsharing.app.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User save(User newUser) {
        newUser = userRepository.save(newUser);
        if(newUser.getUserTypeEnum().equals(UserTypeEnum.ROLE_DRIVER))
        {
            Driver driver = new Driver();
            driver.setUser(newUser);
            driver.setId(newUser.getId());

            driverRepository.save(driver);
        }else{
            Passenger passenger = new Passenger();
            passenger.setUser(newUser);
            passenger.setId(newUser.getId());
            passengerRepository.save(passenger);
        }
        return newUser;
    }

}