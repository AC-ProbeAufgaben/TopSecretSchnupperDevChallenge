package com.amiconsult.topsecretschnupperdevchallenge.security;

import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FoodFriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    FoodFriendsRepository foodFriendsRepository;

    @Override
    public MyUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<FoodFriends> user = Optional.ofNullable(foodFriendsRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found: " + userName)));

        FoodFriends friend = null;
        
        if (user.isPresent()) {
            friend = user.get();
        }
        return new MyUserDetails(friend);
    }
}
