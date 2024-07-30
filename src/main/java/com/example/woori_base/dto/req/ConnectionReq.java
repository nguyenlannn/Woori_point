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
public class ConnectionReq {

    @Size(max = 64, message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String requestId;

    @Size(max = 20, message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String membershipId;

    @Size(max = 30, message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String customerId;

    @Size(max = 16, message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String barCode;
}
