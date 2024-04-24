package com.example.blogsystem.Service;

import com.example.blogsystem.Api.ApiException;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MyUserDetailsService myUserDetailsService;


    public void register(User user){
        String hasPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hasPassword);
        userRepository.save(user);
    }

    public void updateUser(Integer userId, User user){
        User u = userRepository.findUserById(userId);
        if (u == null){
            throw new ApiException("user not found!");
        }
    }


    public User getUser(Integer id){
        User user = userRepository.findUserById(id);
        if (user == null){
            throw new ApiException("user not found!");
        }
        return user;
    }


    public void deleteUser(Integer id){
        User user = userRepository.findUserById(id);
        if (user == null){
            throw new ApiException("user not found!");
        }
        userRepository.delete(user);
    }


    public UserDetails login(String username, String password){
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
        if (new BCryptPasswordEncoder().matches(password, userDetails.getPassword())){
            return userDetails;
        } else {
            throw new ApiException("Invalid username or password!");
        }
    }
    public void logout(){

    }
}
