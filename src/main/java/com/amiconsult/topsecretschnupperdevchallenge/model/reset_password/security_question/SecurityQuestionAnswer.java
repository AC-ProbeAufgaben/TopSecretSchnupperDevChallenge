package com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.security_question;

import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
public class SecurityQuestionAnswer {

    @SequenceGenerator(
            name = "security_question_sequence",
            sequenceName = "security_question_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "security_question_sequence"
    )
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private SecurityQuestion question;

    @Column(nullable = false)
    private String answer;

    @Column
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "securityQuestionAnswer", cascade = CascadeType.ALL)
    @JsonBackReference
    private FoodFriends foodFriend;

    public SecurityQuestionAnswer(String answer,
                                  SecurityQuestion securityQuestion,
                                  LocalDateTime createdAt,
                                  FoodFriends foodFriend) {
        this.answer = answer;
        this.question = securityQuestion;
        this.createdAt = createdAt;
        this.foodFriend = foodFriend;
    }
}
