package com.example.woori_base.dto.res;

import com.example.woori_base.dto.req.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckUserRes {
    private List<Customer> customerInfoList;
}
