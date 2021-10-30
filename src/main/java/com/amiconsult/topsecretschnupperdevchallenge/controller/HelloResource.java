package com.amiconsult.topsecretschnupperdevchallenge.controller;

import com.amiconsult.topsecretschnupperdevchallenge.model.AuthenticationRequest;
import com.amiconsult.topsecretschnupperdevchallenge.model.AuthenticationResponse;
import com.amiconsult.topsecretschnupperdevchallenge.security.MyUserDetails;
import com.amiconsult.topsecretschnupperdevchallenge.security.MyUserDetailsService;
import com.amiconsult.topsecretschnupperdevchallenge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


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

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse res) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad Credentials", e);
        }

        final MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse response = new AuthenticationResponse(jwt);

        return ResponseEntity.ok(response);

    }
}
