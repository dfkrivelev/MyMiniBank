package com.minibank.controllers.rest;


import com.minibank.models.User;
import com.minibank.security.JwtTokenProvider;
import com.minibank.services.UserService;
import com.minibank.vo.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Login")
public class LoginControllerRest implements LoginApi{

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public LoginControllerRest(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public ResponseEntity<Map<Object, Object>> login(@RequestBody AuthenticationRequestDTO body) {
        try {
            String username = body.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, body.getPassword()));
            User user = userService.findByEmail(username).get();

            if(user == null) {
                throw new UsernameNotFoundException("User with username: " + username + "not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRole());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            Map<Object, Object> exception = new HashMap<>();
            exception.put("Invalid email", "or Invalid password");
            return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
        }
    }


    @Override
    public ResponseEntity<UserVO> registration(InlineObject2 inlineObject2) {
        User newUser = new User(inlineObject2.getFirstName(), inlineObject2.getLastName(), inlineObject2.getCountry(),
                inlineObject2.getPhoneNumber(), inlineObject2.getEmail(), inlineObject2.getPassword());

        userService.registration(newUser);
        UserVO userVO = UserVO.valueOf(newUser);
        return ResponseEntity.ok(userVO);
    }
}
