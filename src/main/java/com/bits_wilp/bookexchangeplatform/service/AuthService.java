package com.bits_wilp.bookexchangeplatform.service;

import com.bits_wilp.bookexchangeplatform.dto.AuthenticationResponse;
import com.bits_wilp.bookexchangeplatform.dto.LoginDTO;
import com.bits_wilp.bookexchangeplatform.dto.RegisterDTO;
import com.bits_wilp.bookexchangeplatform.entity.Role;
import com.bits_wilp.bookexchangeplatform.entity.Users;
import com.bits_wilp.bookexchangeplatform.util.JWTHelperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsService userDetailsService;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTHelperUtil jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterDTO request) {
        Users user = getUserFromRegisterRequest(request).orElseThrow(() -> new RuntimeException("Error occurred while getting User from request"));
        Users savedUser = userRepository.save(user);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);
        return jwtToken;
    }

    public Optional<Users> getUserFromRegisterRequest(RegisterDTO request) {
        return Optional.of(Users.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.READER)
                .build());
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isNicknameTaken(String nickname) {
        return userRepository.findByEmail(nickname).isPresent();
    }

    public String confirmEmail(String token) {
        String email = jwtService.extractUsername(token);
        Users user = userRepository.findByEmail(email).orElse(null);
        if (user != null && !jwtService.isTokenExpired(token)) {
            userRepository.save(user);
            return "Confirmed";
        }
        return "Confirmation failed";
    }

    public AuthenticationResponse authenticate(LoginDTO request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            return AuthenticationResponse.builder().build();
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        Users user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("User with email " + request.getEmail() + " is not found"));

        String jvtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder().token(jvtToken).build();
    }
}
