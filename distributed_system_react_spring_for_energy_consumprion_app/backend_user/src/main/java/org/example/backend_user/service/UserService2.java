package org.example.backend_user.service;

import lombok.RequiredArgsConstructor;
import org.example.backend_user.dto.LoginDto;
import org.example.backend_user.dto.RegisterDto;
import org.example.backend_user.model.User;
import org.example.backend_user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService2 {
    @Autowired
    private UserRepository userRepository;



/*
    public User register(RegisterDto registerDto)
    {
        User user1=new User();
        user1.setUsername(registerDto.getUsername());
        user1.setPassword(registerDto.getPassword());
        user1.setFirst_name(registerDto.getFirst_name());
        user1.setLast_name(registerDto.getLast_name());
        user1.setAge(registerDto.getAge());
        user1.setRole("Client");
        return userRepository.save(user1);
    }*/

    /*
    public User registerAuth(RegisterDto request){
        var user = new User();
        user.setFirst_name(request.getFirst_name());
        user.setLast_name(request.getLast_name());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("Client");
        return userRepository.save(user);
        //return jwtService.generateToken(user, generateExtraClaims(user));
    }

    public String login(LoginDto authenticationRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()
        );
        authenticationManager.authenticate(authToken);
        User user = userRepository.findByUsername(authenticationRequest.getUsername()).get();
        return jwtService.generateToken(user, generateExtraClaims(user));
    }*/

}
