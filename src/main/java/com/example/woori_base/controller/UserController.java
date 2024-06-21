package com.example.woori_base.controller;

import com.example.woori_base.base.BaseRespon;
import com.example.woori_base.dto.req.LinkReq;
import com.example.woori_base.dto.req.VerifyLinkReq;
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
    public BaseRespon link(@RequestBody LinkReq linkReq){
         return BaseRespon.success("Liên kết tài khoản/thẻ thành công",
                 userService.postLink(linkReq));
    }

//xác thực liên kết
    @PostMapping("/verify-link")
    public BaseRespon verifyLink(@RequestBody VerifyLinkReq verifyLinkReq){
        return BaseRespon.success("Xác thực liên kết thành công",
                userService.verifyLink(verifyLinkReq) );
    }
}
