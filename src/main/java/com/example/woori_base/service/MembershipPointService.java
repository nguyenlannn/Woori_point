package com.example.woori_base.service;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import org.springframework.stereotype.Service;

@Service
public interface MembershipPointService {

    CheckUserRes checkUer(CheckUserReq checkUserReq);

    ConnectionRes connection(ConnectionReq connecttionReq);

    SecureRegistrationRes secureRegistration(SecureRegistrationReq secureRegistrationReq);

    SecureRegistrationNoRes secureRegistrationNo(SecureRegistrationNoReq secureRegistrationNoReq);

    DetailInfoRes detailInfoSearch(DetailInfoReq detailInfoReq);

    void disconnection(DisconnectionReq disconnectionReq);

    PointsSeekingRes pointsSeeking(PointsSeekingReq pointsSeekingReq);

    PointUsageRes pointUsage(PointUsageReq pointUsageReq);
}
