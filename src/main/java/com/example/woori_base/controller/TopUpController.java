package com.example.woori_base.controller;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import com.example.woori_base.service.TopUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController// tầng controller xử lí các yêu cầu
@RequiredArgsConstructor//annotation tiêm
@Validated
@Log4j2
public class TopUpController {

    private final TopUpService topUpService;

    //Giao dịch nạp ví phát sinh với số tiền lớn hơn 1M-sử dụng mã otp
    @PostMapping("/request-topup")
    public RequestTopupRes requestTopup(@RequestBody RequestTopupReq requestTopupReq) {
        log.info("Input request api request topup---------------------");
        log.info(requestTopupReq.toString());
        return topUpService.requestTopup(requestTopupReq);
    }

    //xác thực giao dịch nạp ví của api trên
    @PostMapping("/verify-otp")
    public TopUpConfirmRes verifyOtp(@RequestBody TopUpConfirmReq verifyOtpReq) {
        log.info("Input request api verify otp---------------------");
        log.info(verifyOtpReq.toString());
        return topUpService.verifyOtp(verifyOtpReq);
    }

    //api giao dịch nạp ví và giao dịch rút ví-phân biệt qua mã xử lí prrstDscd
    @PostMapping("/topup")
    public TopupRes topUp(@RequestBody TopupReq topupReq) {
        log.info("Input request api topup---------------------");
        log.info(topupReq.toString());
        return topUpService.topUp(topupReq);
    }

    //api truy vấn trạng thái giao dịch nạp/rút
    @PostMapping("/check-status")
    public CheckStatusRes checkStatus(@RequestBody CheckStatusReq checkStatusReq) {
        log.info("Input request api check status---------------------");
        log.info(checkStatusReq.toString());
        return topUpService.checkStatus(checkStatusReq);
    }

    //api check số dư tài khoản đối tác
    @PostMapping("/balance")
    public BalanceRes balance(@RequestBody BalanceReq balanceReq) {
        log.info("Input request api balance---------------------");
        log.info(balanceReq.toString());
        return topUpService.balance(balanceReq);
    }
}
