package com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.token;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResetPasswordTokenService {

    @Autowired
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    public void saveResetPasswordToken(ResetPasswordToken token) {
        resetPasswordTokenRepository.save(token);
    }

    public Optional<ResetPasswordToken> getToken(String token) {
        return resetPasswordTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return resetPasswordTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}