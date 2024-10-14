package com.shobhit.projectmanagementsystem.service;

import com.shobhit.projectmanagementsystem.model.User;

public interface UserService {
    User findUserProfileByJwt(String jwt) throws Exception ;

    User findUserByEmail(String email) throws Exception ;

    User findUserById(long id) throws Exception ;

    User updateUsersProjectSize(User user,int number) ;


}
