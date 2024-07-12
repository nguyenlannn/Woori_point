package com.example.woori_base.service;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import org.springframework.stereotype.Service;

@Service
public interface PartnerService {

    String createChecksum(CreateChecksumReq createChecksumReq);
    LinkRes postLink(LinkReq linkReq);

    VerifyLinkRes verifyLink(VerifyLinkReq verifyLinkReq);

    UnlinkRes unlink(UnlinkReq unlinkReq);
}
