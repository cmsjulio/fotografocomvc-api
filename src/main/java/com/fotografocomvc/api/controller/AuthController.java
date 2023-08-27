package com.fotografocomvc.api.controller;

import com.fotografocomvc.api.http.resources.request.LoginRequest;
import com.fotografocomvc.api.http.resources.request.RegisterRequest;
import com.fotografocomvc.api.http.resources.response.AuthResponse;
import com.fotografocomvc.api.security.JwtGenerator;
import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Role;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import com.fotografocomvc.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private BaseUserRepository baseUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JwtGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, BaseUserRepository baseUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.baseUserRepository = baseUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("login")
    private ResponseEntity<AuthResponse> login (@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);

    }

    @PostMapping("register")
    private ResponseEntity<String> register (@RequestBody RegisterRequest registerRequest){
        if (baseUserRepository.existsByUsername(registerRequest.getUsername())){
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        BaseUser baseUser = new BaseUser();
        baseUser.setUsername(registerRequest.getUsername());
        baseUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Role role = roleRepository.findByName("USER").get();
        baseUser.setRoles(Collections.singletonList(role));

        baseUserRepository.save(baseUser);

        return new ResponseEntity<>("User successfully registered", HttpStatus.OK);
    }
}





