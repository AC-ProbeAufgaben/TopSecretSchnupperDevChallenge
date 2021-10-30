package com.amiconsult.topsecretschnupperdevchallenge.repository;

import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations= "/application-test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class FoodFriendsRepositoryTest {

    @Autowired
    private FoodFriendsRepository underTest;
    private static FoodFriends friend = new FoodFriends();

    @BeforeAll
    void setup() {
        friend
                .setName("Vince")
                .setLastName("Canger")
                .setEmail("dude@dude.com")
                .setPassword("pass")
                .setActive(true)
                .setRole("ROLE_USER")
                .setFavFoods(Collections.emptySet());
        underTest.save(friend);
    }

    @Test
    void checksIfFindByNameIgnoreCase() {
        // given
        String name = friend.getName();
        // when
        FoodFriends result = underTest.findByNameIgnoreCase(name);
        // then
        assertThat(result, samePropertyValuesAs(friend));
    }

    @Test
    void checksIfFindByNameIgnoreCaseReturnsNull() {
        // given
        String name = "Petteeerrrrs";
        // when
        FoodFriends result = underTest.findByNameIgnoreCase(name);
        // then
        assertNull(result);
    }

    @Test
    void findByEmail() {
        // given
        String email = friend.getEmail();
        // when
        Optional<FoodFriends> result = underTest.findByEmail(email);
        // then
        assertThat(result, samePropertyValuesAs(Optional.ofNullable(friend)));
    }
}