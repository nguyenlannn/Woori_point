package com.example.woori_base.service.serviceimpl;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import com.example.woori_base.service.TopUpService;
import com.example.woori_base.until.ApiCallUntil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class TopUpServiceImpl implements TopUpService {

    @Autowired
    private ApiCallUntil apiCallUntil;

    @Value("${api.urls.request-topup-e-wallet}")
    private String apiResquestToup;

    @Value("${api.urls.topup-confirm-e-wallet}")
    private String apiTopupConfirm;

    @Value("${api.urls.topup-under-1m-e-wallet}")
    private String apiTopupUnder;

    @Value("${api.urls.check-status}")
    private String apiCheckStatus;

    @Value("${api.urls.balance-inquiry}")
    private String apiBalanceInquiry;

    @Override
    public RequestTopupRes requestTopup(RequestTopupReq requestTopupReq) {
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
