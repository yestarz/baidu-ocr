package com.luckysweetheart.ocr.exception;

/**
 * Created by yangxin on 2017/8/11.
 */
public class OCRException extends Exception {

    private Integer code;

    private String msg;

    public OCRException(){
        super();
    }

    public OCRException(String msg){
        super(msg);
    }

    public OCRException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
