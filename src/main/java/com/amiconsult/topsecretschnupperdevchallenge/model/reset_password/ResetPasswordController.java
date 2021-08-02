package com.amiconsult.topsecretschnupperdevchallenge.model.reset_password;


import com.amiconsult.topsecretschnupperdevchallenge.email_sender.ResetPasswordRequest;
import com.amiconsult.topsecretschnupperdevchallenge.model.ChangePassRequest;
import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriendsService;
import com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.security_question.SecurityQuestion;
import com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.security_question.SecurityQuestionAnswer;
import com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.security_question.SecurityQuestionAnswerRepository;
import com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.security_question.SecurityQuestionRepository;
import com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.token.ResetPasswordToken;
import com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.token.ResetPasswordTokenService;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FoodFriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/reset-password")
public class ResetPasswordController {
    @Autowired
    ResetPasswordService resetPasswordService;
    @Autowired
    FoodFriendsRepository foodFriendsRepository;
    @Autowired
    SecurityQuestionRepository securityQuestionRepository;
    @Autowired
    SecurityQuestionAnswerRepository securityQuestionAnswerRepository;
    @Autowired
    ResetPasswordTokenService resetPasswordTokenService;
    @Autowired
    FoodFriendsService foodFriendsService;
    @Autowired
    BCryptPasswordEncoder bcrypt;

    @PostMapping
    public ResponseEntity<ResetPasswordRequest> reset(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        System.out.println(resetPasswordRequest);
        resetPasswordService.reset(resetPasswordRequest.getEmail());
        return new ResponseEntity<>(resetPasswordRequest, HttpStatus.OK);

    }

    //TODO: would this be better as a GET or POST request?
    @GetMapping(path = "/reset")
    @ResponseBody
    public ResponseEntity confirm(@RequestParam("token") String tokenString) {
        System.out.println("reset password token: " + tokenString);
        return new ResponseEntity(resetPasswordService.confirmToken(tokenString), HttpStatus.OK);
    }

    //TODO: would this be better as a POST or PUT request?
    @PostMapping(path = "/reset")
    public ResponseEntity<FoodFriends> newPassword(@RequestBody ResetPasswordSecurityRequest authRequest) throws ResourceNotFoundException {
        FoodFriends foodFriend = foodFriendsRepository.findByEmail(authRequest.getUsername()).orElseThrow(ResourceNotFoundException::new);

        SecurityQuestionAnswer securityQuestionAnswer = securityQuestionAnswerRepository.findByFoodFriendId(foodFriend.getId()).orElseThrow(ResourceNotFoundException::new);

        Long questionIdFromDb = securityQuestionAnswer.getQuestion().getId();
        Boolean questionIdMatches = questionIdFromDb == authRequest.getSecurityQuestionId();

        String encodedAnswerFromDb = securityQuestionAnswer.getAnswer();
        Boolean encodedAnswerMatches = bcrypt.matches(authRequest.getSecurityQuestionAnswer(), encodedAnswerFromDb);

        if (!encodedAnswerMatches || !questionIdMatches) {
            System.out.println("\n<><><> Failed to authenticate <><><>\n");
            throw new IllegalStateException("Failed to Authenticate");
        } else {
            System.out.println("\n::: Security answer matches :::\n");
        }

        ChangePassRequest changePassRequest = new ChangePassRequest();
        changePassRequest.setConfirmPassword(authRequest.getPassword());
        foodFriendsService.changePassword(foodFriend, changePassRequest);
        return new ResponseEntity<>(foodFriendsRepository.save(foodFriend), HttpStatus.OK);
    }

    @GetMapping(path = "/security-questions")
    public List<SecurityQuestion> getSecurityQuestions() {

        return securityQuestionRepository.findAll();
    }


}