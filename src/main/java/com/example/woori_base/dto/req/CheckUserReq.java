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
public class CheckUserReq {

    @Size(max = 20,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String membersShipId;

    @Size(max = 20,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String matchingType;

    @Size(max = 30,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String matchingValue;

    @Size(max = 25)
    private String dateOfBirth;
}
