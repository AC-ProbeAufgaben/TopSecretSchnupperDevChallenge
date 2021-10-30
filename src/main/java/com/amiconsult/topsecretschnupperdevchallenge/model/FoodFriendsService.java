package com.amiconsult.topsecretschnupperdevchallenge.model;

import com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.security_question.SecurityQuestion;
import com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.security_question.SecurityQuestionAnswer;
import com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.security_question.SecurityQuestionAnswerRepository;
import com.amiconsult.topsecretschnupperdevchallenge.model.reset_password.security_question.SecurityQuestionRepository;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FavFoodRepository;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FoodFriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;


@Service
public class FoodFriendsService {

    @Autowired
    FavFoodRepository favFoodRepository;

    @Autowired
    FoodFriendsRepository foodFriendsRepository;

    @Autowired
    SecurityQuestionAnswerRepository securityQuestionAnswerRepository;

    @Autowired
    SecurityQuestionRepository securityQuestionRepository;

    @Autowired
    BCryptPasswordEncoder bcrypt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<FoodFriends> findAllFriends() {
        return foodFriendsRepository.findAll();
    }

    public boolean checkName(FoodFriends friend) {

        boolean badName = false;

        List<String> nameList = new ArrayList<>(
                Arrays.asList("Hawkeye", "Clint","Francis","Barton","Sören", "Soeren")
        );


        for (String name : nameList) {
            if (friend.getName().equals(name)) {
                badName = true;
                break;
            }
        }
        return badName;
    }

    public boolean checkPassword(AuthenticationRequest authenticationRequest) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            return true;
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad Credentials", e);
        }
    }

    public FoodFriends changePassword(FoodFriends friend, ChangePassRequest changePassRequest) {
        friend.setPassword(bcrypt.encode(changePassRequest.getConfirmPassword()));
        return friend;
    }

    public FoodFriends checkDbAndSave(FoodFriends friend) {
        FoodFriends newFriend = new FoodFriends();
        newFriend.setName(friend.getName());
        newFriend.setLastName(friend.getLastName());
        newFriend.setEmail(friend.getEmail());
        newFriend.setPassword(bcrypt.encode(friend.getPassword()));
        newFriend.setActive(true);
        newFriend.setRole("ROLE_USER");

        String answerToEncode = friend.getSecurityQuestionAnswer().getAnswer();
        SecurityQuestionAnswer securityQuestionAnswer = friend.getSecurityQuestionAnswer();

        newFriend.setSecurityQuestionAnswer(friend.getSecurityQuestionAnswer());
        newFriend.getSecurityQuestionAnswer()
                .setAnswer(bcrypt.encode(answerToEncode))
                .setFoodFriend(newFriend)
                .setCreatedAt(LocalDateTime.now());

        Long securityQuestionIdFromDto = friend.getSecurityQuestionAnswer().getQuestion().getId();

        // TODO: 2) move securityQuestionId from DTO to new object/class and remove it from FoodFriends
        Optional<SecurityQuestion> securityQuestion = securityQuestionRepository.findById(securityQuestionIdFromDto);
        securityQuestion.ifPresent(question -> newFriend.getSecurityQuestionAnswer().setQuestion(question));

        securityQuestionAnswerRepository.save(newFriend.getSecurityQuestionAnswer());

        for (FavFood food : friend.getFavFoods()) {

            FavFood favoritedFood;

            if (favFoodRepository.findByName(food.getName()) == null) {
                favoritedFood = new FavFood();
                favoritedFood.setName(food.getName());
                favFoodRepository.save(favoritedFood);
            } else {
                favoritedFood = favFoodRepository.findByName(food.getName());
            }

            newFriend.addFavFood(favoritedFood);
            favoritedFood.addFoodFriend(newFriend);

        }

        foodFriendsRepository.save(newFriend);
        return newFriend;
    }

    public Optional<FoodFriends> updateFoodFriend(FoodFriends friendToUpdate, FoodFriends updatedFriend) {

        // Remove old foods
        Set<FavFood> foods = friendToUpdate.getFavFoods();
        Set<FavFood> updatedFriendsFoodsToCheck = updatedFriend.getFavFoods();

        Set<FavFood> foodsToRemove = new HashSet<>();

        for (FavFood food : updatedFriendsFoodsToCheck) {
            FavFood foodToRemove = new FavFood();
            if (!foods.contains(food)) {
                foodToRemove = food;
            }
            if (foodToRemove.getName() != null) {
                foodsToRemove.add(foodToRemove);
                foodToRemove.removeFoodFriend(updatedFriend);
            }
        }
        updatedFriend.getFavFoods().removeAll(foodsToRemove);

        // Update friend's new foods
        return Optional.of(updatedFriend)
            .map(friend -> {
                friend.setName(friendToUpdate.getName());
                friend.setLastName(friendToUpdate.getLastName());
                friend.setEmail(friendToUpdate.getEmail());
                friend.setActive(friendToUpdate.isActive());
                friend.setRole(friendToUpdate.getRole());

                for (FavFood food : friendToUpdate.getFavFoods()) {

                    FavFood favoritedFood;
                    // TODO: early returns
                    if (favFoodRepository.findByName(food.getName()) == null) {
                        favoritedFood = new FavFood();
                        favoritedFood.setName(food.getName());
                        favFoodRepository.save(favoritedFood);
                    } else {
                        favoritedFood = favFoodRepository.findByName(food.getName());
                    }

                    friend.addFavFood(favoritedFood);
                    favoritedFood.addFoodFriend(friend);
                }
                return friend;
            });
    }

    public void deleteFriendsFoods(FoodFriends deletedFriend) {
        Set<FavFood> favFoodSet = deletedFriend.getFavFoods();

        if (favFoodSet != null) {
            for (FavFood favFood : favFoodSet) {
                favFood.removeFoodFriend(deletedFriend);
            }
        }

        foodFriendsRepository.delete(deletedFriend);
    }

}
