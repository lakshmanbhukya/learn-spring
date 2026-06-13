package org.example.learnspring.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.learnspring.dto.AuthRequest;
import org.example.learnspring.model.User;
import org.example.learnspring.repository.UserRepository;
import org.example.learnspring.security.JwtProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> signup(@Valid @RequestBody AuthRequest authRequest){
        log.info("Attempting to register : {}", authRequest.getUsername());
        if(userRepository.findByUsername(authRequest.getUsername()).isPresent()){
            log.warn("Tired Username already exists");
            return ResponseEntity.ok("username already taken");
        }
        User user=new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        User savedUser= userRepository.save(user);
        log.info("User is Successfully registered with Id {}: ", savedUser.getId());
        return ResponseEntity.ok("Register Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequest authRequest){
        log.info("Attempting to login : {}", authRequest);
        User user=userRepository.findByUsername
                (authRequest.getUsername()).orElseThrow(()-> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            log.warn("User entered an Invalid Password");
            return ResponseEntity.status(401).body("Incorrect Password");
        }
        return ResponseEntity.ok(jwtProvider.generateToken(user.getUsername()));


    }

}
