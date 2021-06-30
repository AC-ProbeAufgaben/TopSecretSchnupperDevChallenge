package com.amiconsult.topsecretschnupperdevchallenge.model;

import com.amiconsult.topsecretschnupperdevchallenge.repository.FavFoodRepository;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FoodFriendsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


//@RunWith(MockitoJUnitRunner.class) // -- First Implementation
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations= "/application-test.properties")
class FoodFriendsServiceTest {

    @Mock
    FoodFriendsRepository foodFriendsRepository;
    @Mock
    FavFoodRepository favFoodRepository;
    @Mock
    BCryptPasswordEncoder bcrypt;
    @InjectMocks
    FoodFriendsService serviceTest = new FoodFriendsService();
    private static FoodFriends friend;
    private static FavFood food = new FavFood().setName("Pizza");
    private static Set<FavFood> favFoods = new HashSet<>();

    @Test
    void canFindAllFriends() {
        when(foodFriendsRepository.findAll()).thenReturn(Stream.of(
                new FoodFriends()
                        .setName("Vince").setLastName("Canger").setEmail("dude@dude.com"),
                new FoodFriends()
                        .setName("Dude").setLastName("Canger").setEmail("dude@dude.com"))
                .collect(Collectors.toList()));
//        List<FoodFriends> expected = (Stream.of(
//                new FoodFriends()
//                        .setName("Vince").setLastName("Canger").setEmail("dude@dude.com"),
//                new FoodFriends()
//                        .setName("Dude").setLastName("Canger").setEmail("dude@dude.com"))
//                .collect(Collectors.toList()));
//
//        assertThat(expected, samePropertyValuesAs(serviceTest.findAllFriends()));
        serviceTest.findAllFriends();

        verify(foodFriendsRepository).findAll();
    }

    @Test
    void checkName() {
        // given
        friend = new FoodFriends().setName("Dude").setLastName("Canger").setEmail("dude@dude.com");
        // when
        Boolean actual = serviceTest.checkName(friend);
        //then
        assertEquals(false, actual);
    }


    @Test
    void canCheckDbAndSave() {
        // given
        favFoods.add(food);
        friend = new FoodFriends().setName("Dude").setLastName("Canger").setPassword(null).setEmail("dude@dude.com").setActive(true).setRole("ROLE_USER")
            .setFavFoods(favFoods);
        // when
        when(favFoodRepository.findByName("Pizza")).thenReturn(food);
        serviceTest.checkDbAndSave(friend);

        // then
        ArgumentCaptor<FoodFriends> foodFriendsArgumentCaptor =
                ArgumentCaptor.forClass(FoodFriends.class);

        verify(foodFriendsRepository)
                .save(foodFriendsArgumentCaptor.capture());

        FoodFriends capturedFoodFriend = foodFriendsArgumentCaptor.getValue();

        for (FavFood favorite : capturedFoodFriend.getFavFoods()) {
            for (FoodFriends name : favorite.getFavorites()) {
                System.out.println(name.getName());
            }
        }

//        AssertionsForClassTypes.assertThat(capturedFoodFriend).isEqualTo(friend); // -- Second Implementation
        assertThat(capturedFoodFriend, samePropertyValuesAs(friend)); // -- First Implementation

    }

    @Test
    void checkUpdateFoodFriend()  {
        // given
        FoodFriends newFriend = new FoodFriends().setName("Dude").setLastName("Tester").setPassword(null).setEmail("dude@dude.com").setActive(true).setRole("ROLE_USER")
                .setFavFoods(Stream.of(food).collect(Collectors.toCollection(HashSet::new)));

        Set<FavFood> newFoodsToAdd = Stream.of(
                new FavFood().setName("oysters"),
                new FavFood().setName("cookies"),
                new FavFood().setName("Pizza")).collect(Collectors.toCollection(HashSet::new));

        FoodFriends friendToUpdate = new FoodFriends().setName("Duded").setLastName("Tested").setPassword(null).setEmail("dude@dude.com").setActive(true).setRole("ROLE_USER")
                .setFavFoods(newFoodsToAdd);

        // when
        Optional<FoodFriends> updatedFriend = serviceTest.updateFoodFriend(friendToUpdate, newFriend);

        Set<FavFood> updatedFavFoods = updatedFriend.get().getFavFoods();
        Set<FavFood> favFoodsToUpdate = friendToUpdate.getFavFoods();

        //then
        System.out.println(asJsonString(updatedFriend.get()));
        System.out.println(asJsonString(friendToUpdate));
        assertEquals(updatedFriend.get().getName(), friendToUpdate.getName());
        assertThat(updatedFavFoods, samePropertyValuesAs(favFoodsToUpdate));
    }

    @Test
    void deleteFriendsFoods() {
        // given
        favFoods.add(food);
        friend = new FoodFriends().setName("Dude").setLastName("Canger").setPassword(null).setEmail("dude@dude.com").setActive(true).setRole("ROLE_USER")
                .setFavFoods(favFoods);

        // when
        serviceTest.deleteFriendsFoods(friend);

        // then
        ArgumentCaptor<FoodFriends> foodFriendsArgumentCaptor =
                ArgumentCaptor.forClass(FoodFriends.class);

        verify(foodFriendsRepository)
                .delete(foodFriendsArgumentCaptor.capture());

        FoodFriends capturedFoodFriend = foodFriendsArgumentCaptor.getValue();

        AssertionsForClassTypes.assertThat(capturedFoodFriend).isEqualTo(friend);
    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}