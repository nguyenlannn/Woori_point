package com.example.woori_base.controller;

import com.example.woori_base.base.BaseRespon;
import com.example.woori_base.dto.req.*;
import com.example.woori_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController// tầng controller xử lí các yêu cầu
@RequiredArgsConstructor//annotation tiêm
public class UserController {

    private final UserService userService;

    //liên kết tài khoản/thẻ của khách hàng
    @PostMapping("/link")
//    @RequestMapping(value = "/link", method = RequestMethod.POST) cách cũ
    public BaseRespon link(@RequestBody LinkReq linkReq) {
        return BaseRespon.success("Liên kết tài khoản/thẻ thành công",
                userService.postLink(linkReq));
    }

    //xác thực liên kết
    @PostMapping("/verify-link")
    public BaseRespon verifyLink(@RequestBody VerifyLinkReq verifyLinkReq) {
        return BaseRespon.success("Xác thực liên kết thành công",
                userService.verifyLink(verifyLinkReq));
    }

    //hủy liên kết tài khoản ví với tài khoản/thẻ
    @PostMapping("/unlink")
    public BaseRespon unlink(@RequestBody UnlinkReq unlinkReq) {
        return BaseRespon.success("Hủy liên kết thành công",
                userService.unlink(unlinkReq));
    }

    //Giao dịch nạp ví phát sinh với số tiền lớn hơn 1M-sử dụng mã otp
    @PostMapping("/request-topup")
    public BaseRespon requestTopup(@RequestBody RequestTopupReq requestTopupReq) {
        return BaseRespon.success("Hủy liên kết thành công",
                userService.requestTopup(requestTopupReq));
    }

    //xác thực giao dịch nạp ví của api trên
    @PostMapping("/verify-otp")
    public BaseRespon verifyOtp(@RequestBody VerifyOtpReq verifyOtpReq){
        return BaseRespon.success("Xác thực giao dịch thành công",
                userService.verifyOtp(verifyOtpReq));
    }

    //api giao dịch nạp ví và giao dịch rút ví-phân biệt qua mã xử lí prrstDscd
    @PostMapping("/topup")
    public BaseRespon topUp(@RequestBody TopupReq topupReq){
        return BaseRespon.success("mmm",
                userService.topUp(topupReq));
    }

    //api truy vấn trạng thái giao dịch nạp/rút
    @PostMapping("/check-status")
    public BaseRespon checkStatus(@RequestBody CheckStatusReq checkStatusReq){
        return BaseRespon.success("Xxx",
                userService.checkStatus(checkStatusReq));
    }

    //api check số dư tài khoản đối tác
    @PostMapping("/balance")
    public BaseRespon balance(@RequestBody BalanceReq balanceReq){
        return BaseRespon.success("nnn",
                userService.balance(balanceReq));
    }
}
