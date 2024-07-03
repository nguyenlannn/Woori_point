package com.example.woori_base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
@Entity(name = "error code" )
public class ErrorCodeEntity {
    @Id
    private int Id;
    private String code;
    private String errorMessage;
    private int httpCode;
    private String responType;
    private String description;
    private int transactionId;
    private Date createAt;
    private String createBy;
}
