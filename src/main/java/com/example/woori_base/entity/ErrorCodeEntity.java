package com.example.woori_base.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
