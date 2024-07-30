package com.example.woori_base.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecureRegistrationNoReq {

    @Size(max = 64,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String requestId;

    @Size(max = 20,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String membershipId;

    @Size(max = 12,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String mobilePhoneNo;

    @Size(max = 8,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String dateOfBirth;

    @Size(max = 1,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String gender;

    @Size(max = 200,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String customerName;

    @Size(max = 200,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String emailAddress;

    @Size(max = 16,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String barCode;

    private Object addressDetailInfo;

    @Size(max = 100,message = "00031;The required field is omitted. Check the detailed content")
    private String state;

    @Size(max = 200,message = "0031;The required field is omitted. Check the detailed content")
    private String district;

    @Size(max = 200,message = "0031;The required field is omitted. Check the detailed content")
    private String detail1;

    @Size(max = 200,message = "0031;The required field is omitted. Check the detailed content")
    private String detail2;

    @Size(max = 2,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String countryCode;

    @Size(max = 10,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String idType;

    @Size(max = 50,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String idNumber;
}
