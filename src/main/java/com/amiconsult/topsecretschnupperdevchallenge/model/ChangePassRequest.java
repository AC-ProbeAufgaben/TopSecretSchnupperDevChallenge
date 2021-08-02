package com.amiconsult.topsecretschnupperdevchallenge.model;

import lombok.Data;

@Data
public class ChangePassRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
