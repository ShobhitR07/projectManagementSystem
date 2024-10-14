package com.shobhit.projectmanagementsystem.config.controller;

import com.shobhit.projectmanagementsystem.config.JwtProvider;
import com.shobhit.projectmanagementsystem.config.service.UserDetailsServiceImpl;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.repository.UserRepository;
import com.shobhit.projectmanagementsystem.request.LoginRequest;
import com.shobhit.projectmanagementsystem.response.AuthResponse;
import com.shobhit.projectmanagementsystem.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private UserDetailsServiceImpl userDetailsService ;

    @Autowired
    private SubscriptionService subscriptionService ;


    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user){
        User alreadyExists=userRepository.findByEmail(user.getEmail()) ;
        if(alreadyExists!=null){
            return new ResponseEntity<>(new AuthResponse("","email already exists"), HttpStatus.BAD_REQUEST) ;
        }

        User newUser=new User() ;
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFullName(user.getFullName());

        User savedUser=userRepository.save(newUser) ;

        subscriptionService.createSubscription(savedUser) ;



        Authentication authentication=new UsernamePasswordAuthenticationToken(newUser.getEmail(),newUser.getPassword()) ;
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt= JwtProvider.generateToken(authentication) ;

        AuthResponse authResponse=new AuthResponse(jwt,"sign-up successful!") ;

        return new ResponseEntity<>(authResponse,HttpStatus.CREATED) ;



    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signInUser(@RequestBody LoginRequest loginRequest){

        String username= loginRequest.getEmail();
        String password= loginRequest.getPassword();

        Authentication authentication=authenticate(username,password) ;
        SecurityContextHolder.getContext().setAuthentication(authentication) ;

        String jwt= JwtProvider.generateToken(authentication) ;

        AuthResponse authResponse=new AuthResponse(jwt,"sign-in successful!") ;

        return new ResponseEntity<>(authResponse,HttpStatus.OK) ;


    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails=userDetailsService.loadUserByUsername(username) ;
        if(userDetails==null){
            throw new BadCredentialsException("username not valid") ;
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("password invalid") ;
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities()) ;
    }




}
