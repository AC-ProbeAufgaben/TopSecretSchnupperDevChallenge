package com.amiconsult.topsecretschnupperdevchallenge.controller;

import com.amiconsult.topsecretschnupperdevchallenge.model.AuthenticationRequest;
import com.amiconsult.topsecretschnupperdevchallenge.model.AuthenticationResponse;
import com.amiconsult.topsecretschnupperdevchallenge.security.MyUserDetailsService;
import com.amiconsult.topsecretschnupperdevchallenge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class HelloResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping()
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password (JWT)", e);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
}
