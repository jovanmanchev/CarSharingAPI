package com.carsharing.app.service.impl;

import com.carsharing.app.dto.Authentication.JwtAuthenticationResponse;
import com.carsharing.app.dto.Authentication.SignInRequest;
import com.carsharing.app.dto.Authentication.SignUpRequest;
import com.carsharing.app.enums.UserTypeEnum;
import com.carsharing.app.repository.UserRepository;
import com.carsharing.app.model.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

import static java.lang.Character.toUpperCase;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(SignUpRequest request) {

        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhone())
                .encryptedPassword(passwordEncoder.encode(request.getPassword()))
                .userTypeEnum(UserTypeEnum.valueOf(request.getType().toUpperCase()))
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().role(user.getUserTypeEnum().toString())
                .id(user.getId())
                .firstName(user.getFirstName()).lastName(user.getLastName()).token(jwt).build();
    }


    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().role(user.getUserTypeEnum().toString())
                .firstName(user.getFirstName()).lastName(user.getLastName())
                .id(user.getId())
                .token(jwt).build();
    }

}