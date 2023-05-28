package com.minibank.controllers.rest;


import com.minibank.models.User;
import com.minibank.services.UserService;
import com.minibank.vo.InlineObject1;
import com.minibank.vo.InlineObject2;
import com.minibank.vo.UserVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserVO> login(InlineObject1 inlineObject1) {
        return null;
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
