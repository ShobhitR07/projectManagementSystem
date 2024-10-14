package com.shobhit.projectmanagementsystem.service.impl;

import com.shobhit.projectmanagementsystem.config.JwtProvider;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.repository.UserRepository;
import com.shobhit.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository ;
    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email= JwtProvider.getEmailFromToken(jwt) ;
        return findUserByEmail(email) ;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user=userRepository.findByEmail(email) ;
        if(user==null){
            throw new Exception("user not found") ;
        }

        return user;
    }

    @Override
    public User findUserById(long id) throws Exception {
        Optional<User> optionalUser=userRepository.findById(id) ;
        if(optionalUser.isEmpty()){
            throw new Exception("user not found") ;
        }
        return optionalUser.get() ;
    }

    @Override
    public User updateUsersProjectSize(User user, int number) {
        user.setProjectSize(user.getProjectSize()+number);
        return userRepository.save(user) ;
    }
}
