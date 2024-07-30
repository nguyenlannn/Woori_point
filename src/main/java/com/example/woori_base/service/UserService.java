package com.example.woori_base.service;

import com.example.woori_base.dto.req.LoginReq;
import com.example.woori_base.dto.req.UserReq;
import com.example.woori_base.dto.res.LoginRes;
import com.example.woori_base.dto.res.UserRes;

public interface UserService {
    UserRes createAccount(UserReq userReq);
    LoginRes login(LoginReq loginReq);
}
