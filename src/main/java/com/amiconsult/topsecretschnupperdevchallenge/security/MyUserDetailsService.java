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
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<FoodFriends> user = foodFriendsRepository.findByEmail(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not Found: "+userName));

        return user.map(MyUserDetails::new).get();
    }
}
