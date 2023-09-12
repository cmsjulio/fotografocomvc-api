package com.fotografocomvc.api.controller;

import com.fotografocomvc.api.http.resources.request.*;
import com.fotografocomvc.api.http.resources.response.AuthResponse;
import com.fotografocomvc.api.http.resources.response.RegisterCustomerResponse;
import com.fotografocomvc.api.http.resources.response.RegisterPhotographerResponse;
import com.fotografocomvc.api.http.resources.response.TokenRefreshResponse;
import com.fotografocomvc.api.security.JwtManager;
import com.fotografocomvc.core.mapper.CustomerMapper;
import com.fotografocomvc.core.mapper.PhotographerMapper;
import com.fotografocomvc.domain.exception.BusinessException;
import com.fotografocomvc.domain.exception.TokenRefreshException;
import com.fotografocomvc.domain.model.*;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import com.fotografocomvc.domain.repository.RoleRepository;
import com.fotografocomvc.domain.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.stream.Collectors;

import static com.fotografocomvc.api.security.SecurityConstants.ACCESS_TOKEN_EXPIRATION;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private BaseUserRepository baseUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JwtManager jwtManager;

    private RefreshTokenService refreshTokenService;

    private final BaseUserService baseUserService;

    private final CustomerService customerService;

    private final PhotographerService photographerService;

    private final CustomerMapper customerMapper;

    private final AccessTokenService accessTokenService;

    private final PhotographerMapper photographerMapper;
    public AuthController(AuthenticationManager authenticationManager, BaseUserRepository baseUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtManager jwtManager, RefreshTokenService refreshTokenService, BaseUserService baseUserService, CustomerService customerService, PhotographerService photographerService, CustomerMapper customerMapper, AccessTokenService accessTokenService, PhotographerMapper photographerMapper) {
        this.authenticationManager = authenticationManager;
        this.baseUserRepository = baseUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtManager = jwtManager;
        this.refreshTokenService = refreshTokenService;
        this.baseUserService = baseUserService;
        this.customerService = customerService;
        this.photographerService = photographerService;
        this.customerMapper = customerMapper;
        this.accessTokenService = accessTokenService;
        this.photographerMapper = photographerMapper;
    }

    @PostMapping("/login")
    private ResponseEntity<AuthResponse> login (@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BaseUser baseUser = baseUserService.findByUsername(userDetails.getUsername()).get();
        // TODO excluir tokens do usuário se existirem AQUI NESTA LINHA

        AccessToken accessToken = AccessToken.builder()
                .tokenString(jwtManager.generateAccessJWT(authentication))
                .baseUser(baseUser)
                .build();
        accessTokenService.createToken(accessToken);

        RefreshToken refreshToken = RefreshToken.builder()
                        .tokenString(jwtManager.generateRefreshJWT(authentication))
                        .baseUser(baseUser)
                        .build();
        refreshTokenService.createToken(refreshToken);

        baseUser.setAccessToken(accessToken);
        baseUser.setRefreshToken(refreshToken);
        baseUserService.update(baseUser);

        // TODO refatorar AuthResponse
        AuthResponse authResponse = new AuthResponse();
        authResponse.setEmail(baseUser.getUsername());
        authResponse.setAccessToken(accessToken.getTokenString());
        authResponse.setRefreshToken(refreshToken.getTokenString());
        authResponse.setRoles(baseUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()));


        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    @PostMapping("/register-customer")
    private ResponseEntity<RegisterCustomerResponse> registerCustomer (@RequestBody RegisterCustomerRequest registerCustomerRequest){
        if (baseUserService.existsByUsername(registerCustomerRequest.getUsername())){
            throw new BusinessException("Username is taken");
        }

        BaseUser baseUser = new BaseUser();
        baseUser.setUsername(registerCustomerRequest.getUsername());
        baseUser.setPassword(passwordEncoder.encode(registerCustomerRequest.getPassword()));

        Role role = roleRepository.findByName("CUSTOMER").get();
        baseUser.setRoles(Collections.singletonList(role));
        BaseUser savedBaseUser =  baseUserService.save(baseUser);

        Customer customer = Customer.builder()
                .baseUser(savedBaseUser)
                .name(registerCustomerRequest.getName())
                .build();

        Customer savedCustomer = customerService.save(customer);

        RegisterCustomerResponse registerCustomerResponse = customerMapper.customerToRegisterResponse(savedCustomer);

        return new ResponseEntity<>(registerCustomerResponse, HttpStatus.CREATED);

    }

    @PostMapping("/register-photographer")
    private ResponseEntity<RegisterPhotographerResponse> registerPhotographer (@Valid @RequestBody RegisterPhotographerRequest registerPhotographerRequest){
        if (baseUserService.existsByUsername(registerPhotographerRequest.getUsername())){
            throw new BusinessException("Username is taken");
        }

        BaseUser baseUser = new BaseUser();
        baseUser.setUsername(registerPhotographerRequest.getUsername());
        baseUser.setPassword(passwordEncoder.encode(registerPhotographerRequest.getPassword()));

        Role role = roleRepository.findByName("PHOTOGRAPHER").get();
        baseUser.setRoles(Collections.singletonList(role));
        BaseUser savedBaseUser =  baseUserService.save(baseUser);

        Photographer photographer = Photographer.builder()
                .baseUser(savedBaseUser)
                .name(registerPhotographerRequest.getName())
                .gender(registerPhotographerRequest.getGender())
                .bio(registerPhotographerRequest.getBio())
                .phone(registerPhotographerRequest.getPhone())
                .aboutMe(registerPhotographerRequest.getAboutMe())
                .build();

        Photographer savedPhotographer = photographerService.save(photographer);

        RegisterPhotographerResponse registerPhotographerResponse = photographerMapper.photographerToRegisterResponse(savedPhotographer);

        return new ResponseEntity<>(registerPhotographerResponse, HttpStatus.CREATED);

    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getBaseUser)
                .map(user -> {
                    String token = jwtGenerator.generateTokenByEmail(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        refreshTokenService.deleteByUserId(baseUserRepository.findByUsername(username).get().getId());
        return ResponseEntity.ok("Log out successful!" + username);
    }
}





