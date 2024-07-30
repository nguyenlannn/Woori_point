package com.example.woori_base.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointUsageReq {

    @Size(max = 64,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String requestId;

    @Size(max = 30,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String customerId;

    @Size(max = 30,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String usagePoints;
}
