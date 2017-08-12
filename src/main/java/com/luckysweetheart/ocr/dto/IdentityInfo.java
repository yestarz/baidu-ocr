package com.luckysweetheart.ocr.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by yangxin on 2017/8/11.
 */
public class IdentityInfo implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 民族
     */
    private String nation;

    /**
     * 出生日期(yyyy-MM-dd)
     */
    private String birthday;

    /**
     * 住址
     */
    private String address;

    /**
     * 身份证号码
     */
    private String idCard;

    /***********************************************/

    /**
     * 身份证失效时间(yyyy-MM-dd)
     */
    private String overdueTime;

    /**
     * 签发机关
     */
    private String issuingAuthority;

    /**
     * 签发日期
     */
    private String issuingTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(String overdueTime) {
        this.overdueTime = overdueTime;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getIssuingTime() {
        return issuingTime;
    }

    public void setIssuingTime(String issuingTime) {
        this.issuingTime = issuingTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
