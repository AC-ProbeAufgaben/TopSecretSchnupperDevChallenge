package com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.security_question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityQuestionAnswerRepository extends JpaRepository<SecurityQuestionAnswer, Long> {
    Optional<SecurityQuestionAnswer> findByFoodFriendId(Long id);
}

