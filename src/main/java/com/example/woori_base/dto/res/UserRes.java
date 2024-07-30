package com.example.woori_base.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {
    private String apCusNo;//mã khách hàng-duy nhất
    private String email;
    private String cusNm;//tên khách hàng//NGUYEN VAN A
}
