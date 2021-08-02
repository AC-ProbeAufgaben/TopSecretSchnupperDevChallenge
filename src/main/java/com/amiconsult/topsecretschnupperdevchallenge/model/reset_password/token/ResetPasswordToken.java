package com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.token;

import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ResetPasswordToken {

    @SequenceGenerator(
            name = "resetpass_token_sequence",
            sequenceName = "resetpass_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "resetpass_token_sequence"
    )
    private Long id;

    @Column
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            name = "food_friend_id"
    )
    private FoodFriends foodFriend;

    public ResetPasswordToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                              FoodFriends foodFriend) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.foodFriend = foodFriend;
    }
}