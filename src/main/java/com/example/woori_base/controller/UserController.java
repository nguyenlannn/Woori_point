package com.example.woori_base.controller;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import com.example.woori_base.service.UserService;
import com.example.woori_base.until.ApiCallUntil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController// tầng controller xử lí các yêu cầu
@RequiredArgsConstructor//annotation tiêm
public class UserController {

    private final UserService userService;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private ApiCallUntil apiCallUntil;

    //test okhttp
    @GetMapping("/employee")
    public String getExample() {
        String apiUrl = "https://dummy.restapiexample.com/api/v1/employees";
        try {
            String apiResponse = apiCallUntil.makeGetRequest(apiUrl);
            return "GET Response: " + apiResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return "While making GET request, getting Error: " + e.getMessage();
        }
    }

    //test okhttp
    @PostMapping("/create")
    public String postExample() {
        String apiUrl = "https://dummy.restapiexample.com/api/v1/create";

        String requestBody = "{\'name\': \'test\',\'salary\': \'123\',\'age\': \'23\',\'id\': \'25\' }";

        try {
            String apiResponse = apiCallUntil.makePostRequest(apiUrl, requestBody);
            return "POST Response: " + apiResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return "While making POST request, getting Error: " + e.getMessage();
        }
    }

    //liên kết tài khoản/thẻ của khách hàng
    @PostMapping("/link")
////    @RequestMapping(value = "/link", method = RequestMethod.POST) cách cũ
    public LinkRes link(@RequestParam String apiUrl, @RequestBody @Valid LinkReq linkReq) {

        logger.info("Info level log example");

        return userService.postLink(apiUrl, linkReq);
    }

    //xác thực liên kết
    @PostMapping("/verify-link")
    public VerifyLinkRes verifyLink(@RequestBody VerifyLinkReq verifyLinkReq) {
        return userService.verifyLink(verifyLinkReq);
    }

    //hủy liên kết tài khoản ví với tài khoản/thẻ
    @PostMapping("/unlink")
    public UnlinkRes unlink(@RequestBody UnlinkReq unlinkReq) {
        return userService.unlink(unlinkReq);
    }

    //Giao dịch nạp ví phát sinh với số tiền lớn hơn 1M-sử dụng mã otp
    @PostMapping("/request-topup")
    public RequestTopupRes requestTopup(@RequestBody RequestTopupReq requestTopupReq) {
        return userService.requestTopup(requestTopupReq);
    }

    //xác thực giao dịch nạp ví của api trên
    @PostMapping("/verify-otp")
    public VerifyOtpRes verifyOtp(@RequestBody VerifyOtpReq verifyOtpReq) {
        return userService.verifyOtp(verifyOtpReq);
    }

    //api giao dịch nạp ví và giao dịch rút ví-phân biệt qua mã xử lí prrstDscd
    @PostMapping("/topup")
    public TopupRes topUp(@RequestBody TopupReq topupReq) {
        return userService.topUp(topupReq);
    }

    //api truy vấn trạng thái giao dịch nạp/rút
    @PostMapping("/check-status")
    public CheckStatusRes checkStatus(@RequestBody CheckStatusReq checkStatusReq) {
        return userService.checkStatus(checkStatusReq);
    }

    //api check số dư tài khoản đối tác
    @PostMapping("/balance")
    public BalanceRes balance(@RequestBody BalanceReq balanceReq) {
        return userService.balance(balanceReq);
    }
}
