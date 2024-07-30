package com.example.woori_base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
@Entity(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    private Integer partnerId;
    private Date tmsDt;
    private Date tmsTm;
    private String trnSrno;
    private String prrstDscd;
    private String actNo;
    private Integer linkType;
    private Date createAt;
    private Date updateAt;
    private String createBy;
    private String updateBy;
}