package com.example.woori_base.service.serviceimpl;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
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
        //TODO
        return null;
    }

    @Override
    public UnlinkRes unlink(UnlinkReq unlinkReq) {
        //TODO
        return null;
    }

    @Override
    public RequestTopupRes requestTopup(RequestTopupReq requestTopupReq) {
        //TODO
        return null;
    }

    @Override
    public VerifyOtpRes verifyOtp(VerifyOtpReq verifyOtpReq) {
        return null;
    }

    @Override
    public TopupRes topUp(TopupReq topupReq) {
        return null;
    }

    @Override
    public CheckStatusRes checkStatus(CheckStatusReq checkStatusReq) {
        return null;
    }

    @Override
    public BalanceRes balance(BalanceReq balanceReq) {
        return null;
    }
}
