package com.example.woori_base.service;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public interface UserService {
    LinkRes postLink(String apiUrl, LinkReq linkReq);

    VerifyLinkRes verifyLink(VerifyLinkReq verifyLinkReq);

    UnlinkRes unlink(UnlinkReq unlinkReq);

    RequestTopupRes requestTopup(RequestTopupReq requestTopupReq);

    VerifyOtpRes verifyOtp(VerifyOtpReq verifyOtpReq);

    TopupRes topUp(TopupReq topupReq);

    CheckStatusRes checkStatus(CheckStatusReq checkStatusReq);

    BalanceRes balance(BalanceReq balanceReq);
}
