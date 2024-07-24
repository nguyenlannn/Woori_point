package com.example.woori_base.controller;

import com.example.woori_base.base.BaseRequest;
import com.example.woori_base.dto.req.LinkReq;
import com.example.woori_base.dto.req.UnlinkReq;
import com.example.woori_base.dto.req.VerifyLinkReq;
import com.example.woori_base.dto.res.LinkRes;
import com.example.woori_base.dto.res.UnlinkRes;
import com.example.woori_base.dto.res.VerifyLinkRes;
import com.example.woori_base.service.PartnerService;
import com.example.woori_base.until.ApiCallUntil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController// tầng controller xử lí các yêu cầu
@RequiredArgsConstructor//annotation tiêm
@Log4j2
public class PartnerController {

    private final PartnerService partnerService;

    @Autowired
    private ApiCallUntil apiCallUntil;

    //test okhttp với getmapping
//    @GetMapping("/employee")
//    public String getExample() {
//        String apiUrl = "https://dummy.restapiexample.com/api/v1/employees";
//        try {
//            String apiResponse = apiCallUntil.makeGetRequest(apiUrl);
//            return "GET Response: " + apiResponse;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "While making GET request, getting Error: " + e.getMessage();
//        }
//    }

    //test okhttp với postmapping
//    @PostMapping("/create")
//    public String postExample() {
//        String apiUrl = "https://dummy.restapiexample.com/api/v1/create";
//
//        String requestBody = "{\'name\': \'test\',\'salary\': \'123\',\'age\': \'23\',\'id\': \'25\' }";
//
//            String apiResponse = apiCallUntil.makePostRequest(apiUrl, requestBody);
//            return "POST Response: " + apiResponse;
//    }

    @PostMapping("/create-checksum")
    public String createChecksum(@RequestBody BaseRequest baseRequest){

        return partnerService.createChecksum(baseRequest);
    }

    //liên kết tài khoản/thẻ của khách hàng
    @PostMapping("/link")
//  @RequestMapping(value = "/link", method = RequestMethod.POST) cách cũ
    public LinkRes link(@Valid @RequestBody LinkReq linkReq) {
        log.info("Input request api link---------------------");
        log.info(linkReq.toString());
        return partnerService.postLink(linkReq);
    }

    //xác thực liên kết
    @PostMapping("/verify-link")
    public VerifyLinkRes verifyLink(@Valid @RequestBody VerifyLinkReq verifyLinkReq) {
        log.info("Input request api verify link---------------------");
        log.info(verifyLinkReq.toString());
        return partnerService.verifyLink(verifyLinkReq);
    }

    //hủy liên kết tài khoản ví với tài khoản/thẻ
    @PostMapping("/unlink")
    public UnlinkRes unlink(@Valid @RequestBody UnlinkReq unlinkReq) {
        log.info("Input request api unlink---------------------");
        log.info(unlinkReq.toString());
        return partnerService.unlink(unlinkReq);
    }
}
