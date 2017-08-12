package com.luckysweetheart.ocr;

import com.baidu.aip.ocr.AipOcr;
import com.luckysweetheart.ocr.cons.Cons;
import com.luckysweetheart.ocr.dto.BankCardInfo;
import com.luckysweetheart.ocr.dto.IdentityInfo;
import com.luckysweetheart.ocr.exception.OCRException;
import com.luckysweetheart.ocr.util.DateUtil;
import com.luckysweetheart.ocr.util.OCRUtil;
import com.luckysweetheart.ocr.util.ThumbnailUtilService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by yangxin on 2017/8/12.
 */
public class OCRClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int MAX_LENGTH = 1024 * 150;

    private AipOcr aipOcr;

    private byte[] bytes;

    private ThumbnailUtilService thumbnailUtilService;

    public OCRClient(byte[] bytes) {
        this.bytes = bytes;
        this.aipOcr = new AipOcr(Cons.APP_ID, Cons.APP_KEY, Cons.APP_SECRET);
        this.aipOcr.setConnectionTimeoutInMillis(6000);
        thumbnailUtilService = new ThumbnailUtilService();
    }

    public IdentityInfo getIdentityInfo(boolean isFront) throws OCRException, IOException {
        logger.info("开始调用百度身份证识别接口... at {} ", DateUtil.formatNow());
        long start = System.currentTimeMillis();
        HashMap<String, String> options = new HashMap<String, String>();
        if (bytes.length > MAX_LENGTH) {
            bytes = thumbnailUtilService.createThumbnail(bytes);
            logger.info("压缩过后的文件大小为：{} KB", bytes.length / 1024);
        }
        options.put("detect_direction", "true");
        JSONObject idCard = aipOcr.idcard(bytes, isFront, options);
        long end = System.currentTimeMillis();
        logger.info("调用百度身份证识别接口调用结束... at {} , cost {} ms", DateUtil.formatNow(), end - start);
        return OCRUtil.getIdentityInfo(idCard);
    }

    public IdentityInfo getIdentityInfo() throws OCRException, IOException {
        return this.getIdentityInfo(true);
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
        logger.info("调用百度身份证识别接口调用结束 at {} , cost {} ms", DateUtil.formatNow(), end - start);
        return OCRUtil.getBankCardInfo(response);
    }
}
