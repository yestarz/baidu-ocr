package com.luckysweetheart.ocr.service;

import com.baidu.aip.ocr.AipOcr;
import com.luckysweetheart.ocr.dto.BankCardInfo;
import com.luckysweetheart.ocr.dto.IdentityInfo;
import com.luckysweetheart.ocr.exception.OCRException;
import com.luckysweetheart.ocr.util.DateUtil;
import com.luckysweetheart.ocr.util.OCRUtil;
import com.luckysweetheart.ocr.util.ThumbnailUtilService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

/**
 * 百度文字识别服务，目前支持身份证识别、银行卡识别
 * Created by yangxin on 2017/8/11.
 */
@Service
public class BaiduOCRService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int MAX_LENGTH = 1024 * 150;

    @Resource
    private AipOcr aipOcr;

    @Resource
    private ThumbnailUtilService thumbnailUtilService;

    /**
     * 读取身份证信息
     *
     * @param bytes   文件
     * @param isFront 是否为身份证正面，是为true，不是为false
     * @return
     * @throws OCRException
     */
    public IdentityInfo getIdentityInfo(byte[] bytes, boolean isFront) throws OCRException, IOException {
        logger.info("开始调用百度身份证识别接口... at {} ", DateUtil.formatNow());
        long start = System.currentTimeMillis();

        if (bytes.length > MAX_LENGTH) {
            bytes = thumbnailUtilService.createThumbnail(bytes);
            logger.info("压缩过后的文件大小为：{} KB", bytes.length / 1024);
        }

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        JSONObject idCard = aipOcr.idcard(bytes, isFront, options);
        long end = System.currentTimeMillis();
        logger.info("调用百度身份证识别接口调用结束... at {} , cost {} ms", DateUtil.formatNow(), end - start);
        return OCRUtil.getIdentityInfo(idCard);
    }

    /**
     * 读取身份证正面信息
     *
     * @param bytes
     * @return
     * @throws OCRException
     */
    public IdentityInfo getIdentityInfo(byte[] bytes) throws OCRException, IOException {
        return getIdentityInfo(bytes, true);
    }

    /**
     * 读取银行卡信息
     *
     * @param bytes
     * @return
     * @throws OCRException
     */
    public BankCardInfo getBankCardInfo(byte[] bytes) throws OCRException, IOException {
        logger.info("开始调用百度银行卡识别接口... at {} ", DateUtil.formatNow());
        long start = System.currentTimeMillis();
        if (bytes.length > MAX_LENGTH) {
            bytes = thumbnailUtilService.createThumbnail(bytes);
            logger.info("压缩银行卡图片过后的文件大小为：{} KB", bytes.length / 1024);
        }
        JSONObject response = aipOcr.bankcard(bytes);
        long end = System.currentTimeMillis();
        logger.info("调用百度身份证识别接口调用结束... at {} , cost {} ms", DateUtil.formatNow(), end - start);
        return OCRUtil.getBankCardInfo(response);
    }

}
