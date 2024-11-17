package com.bits_wilp.bookexchangeplatform.controllers;

import com.bits_wilp.bookexchangeplatform.dto.*;
import com.bits_wilp.bookexchangeplatform.service.AuthService;
import com.bits_wilp.bookexchangeplatform.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO request) {
        if (!authenticationService.isEmailTaken(request.getEmail())) {
            if (!authenticationService.isNicknameTaken(request.getName())) {
                authenticationService.register(request);
                return ResponseEntity.ok().body("User registered successfully");
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder().error(new ErrorDescription(
                    HttpStatus.CONFLICT.value(), "Nickname is already taken")).build());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder().error(new ErrorDescription(
                HttpStatus.CONFLICT.value(), "Email address is already taken")).build());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginDTO request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        if (authenticationResponse.getToken() != null && !authenticationResponse.getToken().isEmpty()) {
            return ResponseEntity.ok().body(SuccessResponse.builder().data(authenticationResponse).build());
        }
        return ResponseEntity.badRequest().body(ErrorResponse.builder().error(new ErrorDescription(
                HttpStatus.BAD_REQUEST.value(), "Invalid data credentials")).build());
    }
}
