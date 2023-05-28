package com.minibank.controllers.rest;


import com.minibank.models.User;
import com.minibank.services.UserService;
import com.minibank.vo.InlineObject1;
import com.minibank.vo.InlineObject2;
import com.minibank.vo.InlineObject3;
import com.minibank.vo.UserVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Login")
public class LoginControllerRest implements LoginApi{

    private final UserService userService;

    @Autowired
    public LoginControllerRest(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserVO> login(InlineObject2 inlineObject2) {
        return null;
    }

    @Override
    public ResponseEntity<UserVO> registration(InlineObject3 inlineObject3) {
        User newUser = new User(inlineObject3.getFirstName(), inlineObject3.getLastName(), inlineObject3.getCountry(),
                inlineObject3.getPhoneNumber(), inlineObject3.getEmail(), inlineObject3.getPassword());

        userService.registration(newUser);
        UserVO userVO = UserVO.valueOf(newUser);
        return ResponseEntity.ok(userVO);
    }
}
