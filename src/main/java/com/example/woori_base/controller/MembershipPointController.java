package com.example.woori_base.controller;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import com.example.woori_base.service.MembershipPointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MembershipPointController {

    private final MembershipPointService membershipPointService;

    //xác nhận thành viên đối tác có phải là khách hàng của woori hay không
    @PostMapping("/check-user")
    public CheckUserRes checkUser(@RequestBody @Valid CheckUserReq checkUserReq){
        return membershipPointService.checkUer(checkUserReq);
    }

    //thực hiện liên kết thành vien đối tác với tài khoản tại woori
    @PostMapping("/connection")
    public ConnectionRes connection(@RequestBody ConnectionReq connectionReq){
        return membershipPointService.connection(connectionReq);
    }
    //todo đăng kí thành viên woori và thực hiện liên kết-có mã hoá
    @PostMapping("/secure-registration-encode")
    public SecureRegistrationRes secureRegistration(@RequestBody SecureRegistrationReq secureRegistrationReq){
        return membershipPointService.secureRegistration(secureRegistrationReq);
    }

   //todo đăng kí thành viên woori và thực hiện liên kết-không mã hoá
    @PostMapping("/secure-registration-no-encode")
    public SecureRegistrationNoRes secureRegistrationNo(@RequestBody SecureRegistrationNoReq secureRegistrationNoReq){
        return membershipPointService.secureRegistrationNo(secureRegistrationNoReq);
    }

    //thực hiện tra cứu thông tin khách hàng tại woori
    @PostMapping("/detail-info-search")
    public DetailInfoRes detailInfoSearch(@RequestBody DetailInfoReq detailInfoReq){
        return membershipPointService.detailInfoSearch(detailInfoReq);
    }

    //thực hiện huỷ liên kết của các thaành viên
    @PostMapping("/disconnection")
    public void disconnection(@RequestBody DisconnectionReq disconnectionReq){
        membershipPointService.disconnection(disconnectionReq);
    }

    //tra cứu điểm so còn lại của khách hàng taại woori
    @PostMapping("/points-seeking")
    public PointsSeekingRes pointsSeeking(@RequestBody PointsSeekingReq pointsSeekingReq){
        return membershipPointService.pointsSeeking(pointsSeekingReq);
    }
    // thực hiện chuyển đổi điểm woori point sang đểm cuủa đôối tác
    @PostMapping("/points-usage")
    public PointUsageRes pointUsage(@RequestBody PointUsageReq pointUsageReq){
        return membershipPointService.pointUsage(pointUsageReq);
    }
}
