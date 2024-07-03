package com.example.woori_base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
@Entity(name = "verify code")
public class VerifyCodeEntity {
    @Id
    private Integer id;
    private Integer code;
    private Date createAt;
    private String createBy;
    private Integer transactionId;
}
