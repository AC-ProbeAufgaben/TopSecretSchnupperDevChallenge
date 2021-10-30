package com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.security_question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "SECURITY_QUESTION")
public class SecurityQuestion {

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

    private String question;
}
