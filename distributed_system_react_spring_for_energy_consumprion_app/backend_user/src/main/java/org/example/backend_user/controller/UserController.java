package org.example.backend_user.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend_user.dto.LoginDto;
import org.example.backend_user.dto.RegisterDto;
import org.example.backend_user.exception.UserNotFoundException;
import org.example.backend_user.model.User;
import org.example.backend_user.repository.UserRepository;
import org.example.backend_user.service.JwtService;
import org.example.backend_user.service.UserService;
import org.example.backend_user.service.UserService2;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.*;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(value = "http://localhost:3000",allowCredentials = "true")
@RequiredArgsConstructor
public class UserController {

    RestTemplate restTemplate = new RestTemplate();

    //jdbc:mysql://mysql_utilizator:3306/Utilizator

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService2 userService2;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    /*
    @PostMapping("/user")
    User newUser(@RequestBody User newUser)
    {
        return userRepository.save(newUser);
    }

    @GetMapping("/all_users")
    List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping("/all_admins")
    User getAllUsersAdmins()
    {
        List<User> all= userRepository.findAll();
        List<User> admins= new ArrayList<>();
        for(User user:all)
        {
            if(Objects.equals(user.getRole(), "Admin"))
                admins.add(user);
        }
        return admins.getFirst();
    }

    @GetMapping("/user/{user_id}")
    User getUserById(@PathVariable Integer user_id)
    {
        return userRepository.findById(user_id).orElseThrow(()->new UserNotFoundException(user_id));
    }

    @PutMapping("/user/{user_id}")
    User updateUser(@RequestBody User newUser, @PathVariable Integer user_id)
    {
        return userRepository.findById(user_id).map(
                user -> {
                    user.setFirst_name(newUser.getFirst_name());
                    user.setLast_name(newUser.getLast_name());
                    user.setPassword(newUser.getPassword());
                    user.setAge(newUser.getAge());
                    user.setUsername(newUser.getUsername());
                    user.setRole(newUser.getRole());
                    return userRepository.save(user);
                }
        ).orElseThrow(()->new UserNotFoundException(user_id));
    }

    @DeleteMapping("/user/{user_id}")
    String deleteUser(@PathVariable Integer user_id)
    {
        if(!userRepository.existsById(user_id))
        {
            throw new UserNotFoundException(user_id);
        }
        String url="http://localhost:8081/user/"+user_id+"/devices";
        //String url="http://dispozitiveapp:8081/user/"+user_id+"/devices";
        String response=restTemplate.exchange(url, HttpMethod.DELETE, null, String.class).getBody();
        System.out.println(response);
        userRepository.deleteById(user_id);
        return "User with id " + user_id + " was deleted";
    }

    @PostMapping("/login")
    User findLoginUser(@RequestBody LoginDto loginDto)
    {
        Optional <User> user1=userRepository.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        User user2=new User();
        user2.setRole("Invalid username or password");
        return user1.orElse(user2);
    }


    @PostMapping("/register")
    User registerUser(@RequestBody RegisterDto registerDto)
    {
        return userService.register(registerDto);
    }

    @PostMapping("/register/secure")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        return null;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody LoginDto loginDto){
        return null;
    }
   */

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        return userRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        //String url="http://localhost:8081/user/"+userId+"/devices";
        String url="http://dispozitiveapp:8081/user/"+userId+"/devices";
        String response=restTemplate.exchange(url, HttpMethod.DELETE, null, String.class).getBody();
        System.out.println(response);
        userRepository.deleteById(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/register/secure")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(userService.registerAuth(registerDto));
    }

    @PutMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User userDetails) {
        System.out.println("Received PUT request for user ID: " + userId);
        System.out.println("Request body: " + userDetails);

        return userRepository.findById(userId)
                .map(existingUser -> {
                    System.out.println("Existing user found: " + existingUser);
                    existingUser.setFirstName(userDetails.getFirstName());
                    existingUser.setLastName(userDetails.getLastName());
                    existingUser.setAge(userDetails.getAge());
                    existingUser.setUsername(userDetails.getUsername());
                    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                    }
                    existingUser.setRole(userDetails.getRole());

                    User updatedUser = userRepository.save(existingUser);
                    System.out.println("User updated successfully: " + updatedUser);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElseThrow(() -> {
                    System.out.println("User not found with ID: " + userId);
                    return new UserNotFoundException(userId);
                });
    }


    @PostMapping("/api/login/secure")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsername());
            User user = (User) userDetails;
            String token = jwtService.generateToken(userDetails, user.getUserId());

            return ResponseEntity.ok(Map.of(
                    "role", user.getRole(),
                    "user_id", user.getUserId(),
                    "token", token
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }

    @PostMapping("/add/secure")
    public ResponseEntity<User> add(@Valid @RequestBody User registerDto) {
        return ResponseEntity.ok(userService.add(registerDto));
    }

    @GetMapping("/chat/{sender}")
    public String getSenderUsername(@PathVariable Integer sender)
    {
        User s = userRepository.findById(sender)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        return s.getUsername();
    }

    @GetMapping("/admin")
    List<User> getAllUsersAdmins()
    {
        List<User> all= userRepository.findAll();
        List<User> admins= new ArrayList<>();
        for(User user:all)
        {
            if(Objects.equals(user.getRole(), "ADMIN"))
                admins.add(user);
        }
        return admins;
    }

    @GetMapping("/chat/users")
    public ResponseEntity<List<User>> getchatUsers(List<Integer> userIds)
    {
        List<User> users = userRepository.findAllById(userIds);
        return ResponseEntity.ok(users);
    }
}
