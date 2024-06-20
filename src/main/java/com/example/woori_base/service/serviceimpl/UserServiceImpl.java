package com.example.woori_base.service.serviceimpl;

import com.example.woori_base.dto.req.LinkReq;
import com.example.woori_base.dto.req.VerifyLinkReq;
import com.example.woori_base.dto.res.LinkRes;
import com.example.woori_base.dto.res.VerifyLinkRes;
import com.example.woori_base.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public LinkRes postLink(LinkReq userReq) {
//TODO
        return null;
    }

    @Override
    public VerifyLinkRes verifyLink(VerifyLinkReq verifyLinkReq) {
        return null;
    }
}
