package com.bits_wilp.bookexchangeplatform.controllers;

import com.bits_wilp.bookexchangeplatform.dto.JWTRequest;
import com.bits_wilp.bookexchangeplatform.dto.JwtResponse;
import com.bits_wilp.bookexchangeplatform.dto.UserDTO;
import com.bits_wilp.bookexchangeplatform.exceptions.NoRecordFoundException;
import com.bits_wilp.bookexchangeplatform.service.UsersService;
import com.bits_wilp.bookexchangeplatform.util.JWTHelperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food-app/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class JwtController {

    private final UserDetailsService userDetailsService;
    private final JWTHelperUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UsersService userService;


    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JWTRequest jwtRequest){
        System.out.println(jwtRequest);

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        System.out.println(userDetails.getUsername());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
            String generatedToken = jwtUtil.generateToken(userDetails);
            System.out.println(generatedToken);
            UserDTO userDto = this.userService.getUserByEmail(jwtRequest.getEmail());

            JwtResponse jwtResponse = new JwtResponse(generatedToken, userDto);

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

        }catch (RuntimeException e){
            throw new BadCredentialsException("Incorrect Email or Password!!! " + e.getMessage());
        } catch (NoRecordFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
