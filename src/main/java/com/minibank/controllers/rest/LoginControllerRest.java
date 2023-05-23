package com.minibank.controllers.rest;

import com.minibank.vo.InlineObject1;
import com.minibank.vo.InlineObject2;
import com.minibank.vo.UserVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginControllerRest implements LoginApi{
    @Override
    public ResponseEntity<UserVO> login(InlineObject1 inlineObject1) {
        return null;
    }

    @Override
    public ResponseEntity<UserVO> registration(InlineObject2 inlineObject2) {
        return null;
    }
}
