package com.example.woori_base.service;

import com.example.woori_base.dto.req.LinkReq;
import com.example.woori_base.dto.req.VerifyLinkReq;
import com.example.woori_base.dto.res.LinkRes;
import com.example.woori_base.dto.res.VerifyLinkRes;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    LinkRes postLink(LinkReq linkReq);

    VerifyLinkRes verifyLink(VerifyLinkReq verifyLinkReq);
}
