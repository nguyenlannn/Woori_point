package com.example.woori_base.controller;

import com.example.woori_base.dto.req.LoginReq;
import com.example.woori_base.dto.req.UserReq;
import com.example.woori_base.dto.res.LoginRes;
import com.example.woori_base.dto.res.UserRes;
import com.example.woori_base.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/basic")
public class BasicController {

    private final UserService userService;

    //đăng kí
    @PostMapping("/register")
    public UserRes createAccount(@RequestBody @Valid UserReq userReq) {
        return userService.createAccount(userReq);
    }

    //đăng nhập - authen
    @PostMapping("/login")
    public LoginRes login(@RequestBody @Valid LoginReq loginReq) {
        return userService.login(loginReq);
    }

    //refresh-token
//    @PostMapping("/refreshToken")
//    public LoginRes refreshToken(HttpServlet request) {
//        return userService.refreshToken(request);
//    }
}
