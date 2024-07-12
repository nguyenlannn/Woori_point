package com.example.woori_base.service;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import org.springframework.stereotype.Service;

@Service
public interface TopUpService {

    RequestTopupRes requestTopup(RequestTopupReq requestTopupReq);

    VerifyOtpRes verifyOtp(VerifyOtpReq verifyOtpReq);

    TopupRes topUp(TopupReq topupReq);

    CheckStatusRes checkStatus(CheckStatusReq checkStatusReq);

    BalanceRes balance(BalanceReq balanceReq);
}
