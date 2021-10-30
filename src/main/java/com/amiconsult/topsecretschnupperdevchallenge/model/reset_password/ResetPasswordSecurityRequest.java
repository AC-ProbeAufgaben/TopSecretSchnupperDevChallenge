package com.amiconsult.topsecretschnupperdevchallenge.model.reset_password;

import lombok.Data;

@Data
public class ResetPasswordSecurityRequest {
    private String username;
    private String password;
    private int securityQuestionId;
    private String securityQuestionAnswer;
}
