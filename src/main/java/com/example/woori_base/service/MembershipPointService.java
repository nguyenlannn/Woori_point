package com.example.woori_base.service;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import org.springframework.stereotype.Service;

@Service
public interface MembershipPointService {

    CheckUserRes checkUer(CheckUserReq checkUserReq);

    ConnecttionRes connection(ConnecttionReq connecttionReq);

    SecureRegistrationRes secureRegistration(SecureRegistrationReq secureRegistrationReq);

    DetailInfoRes detailInfoSearch(DetailInfoReq detailInfoReq);

    DisconnectionRes disconnection(DisconnectionReq disconnectionReq);

    PointsSeekingRes pointsSeeking(PointsSeekingReq pointsSeekingReq);

    PointUsageRes pointUsage(PointUsageReq pointUsageReq);
}
