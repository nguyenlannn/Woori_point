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

    @PostMapping("/connection")
    public ConnecttionRes connection(@RequestBody ConnecttionReq connecttionReq){
        return membershipPointService.connection(connecttionReq);
    }

    @PostMapping("/secure-registration")
    public SecureRegistrationRes secureRegistration(@RequestBody SecureRegistrationReq secureRegistrationReq){
        return membershipPointService.secureRegistration(secureRegistrationReq);
    }

    @PostMapping("/detail-info-search")
    public DetailInfoRes detailInfoSearch(@RequestBody DetailInfoReq detailInfoReq){
        return membershipPointService.detailInfoSearch(detailInfoReq);
    }

    @PostMapping("/disconnection")
    public DisconnectionRes disconnection(@RequestBody DisconnectionReq disconnectionReq){
        return membershipPointService.disconnection(disconnectionReq);
    }

    @PostMapping("/points-seeking")
    public PointsSeekingRes pointsSeeking(@RequestBody PointsSeekingReq pointsSeekingReq){
        return membershipPointService.pointsSeeking(pointsSeekingReq);
    }

    @PostMapping("/points-usage")
    public PointUsageRes pointUsage(@RequestBody PointUsageReq pointUsageReq){
        return membershipPointService.pointUsage(pointUsageReq);
    }
}
