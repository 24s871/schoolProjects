package org.example.backend_user.service;

import lombok.RequiredArgsConstructor;
import org.example.backend_user.dto.AuthenticationResponse;
import org.example.backend_user.dto.LoginDto;
import org.example.backend_user.dto.RegisterDto;
import org.example.backend_user.model.User;
import org.example.backend_user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User registerAuth(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setAge(registerDto.getAge());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole("CLIENT");
        user.setOnline(false);

        return userRepository.save(user);
    }

    public User add(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user2 = new User();
        user2.setFirstName(user.getFirstName());
        user2.setLastName(user.getLastName());
        user2.setAge(user.getAge());
        user2.setUsername(user.getUsername());
        user2.setPassword(passwordEncoder.encode(user.getPassword()));
        user2.setRole(user.getRole());
        user2.setOnline(false);

        return userRepository.save(user2);
    }

    /*
    public AuthenticationResponse authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user, user.getUserId());

        return AuthenticationResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .role(user.getRole())
                .build();
    }*/

}
