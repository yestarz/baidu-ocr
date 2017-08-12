package com.luckysweetheart.ocr.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 银行卡信息
 * Created by yangxin on 2017/8/11.
 */
public class BankCardInfo implements Serializable {

    /**
     * 银行卡号
     */
    private String cardNumber;

    /**
     * 银行名
     */
    private String bankName;

    /**
     * 银行卡类型（信用卡、储蓄卡）
     */
    private String cardType;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
