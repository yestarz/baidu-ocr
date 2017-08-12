package com.luckysweetheart.ocr.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luckysweetheart.ocr.dto.BankCardInfo;
import com.luckysweetheart.ocr.dto.IdentityInfo;
import com.luckysweetheart.ocr.exception.OCRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yangxin on 2017/8/11.
 */
public class OCRUtil {

    private static Logger logger = LoggerFactory.getLogger(OCRUtil.class);

    private static String getErrorMsg(Integer code) {

        String msg;

        switch (code) {
            case 1:
                msg = "服务器内部错误，请再次请求， 如果持续出现此类错误，请通过QQ群（224994340）或工单联系技术支持团队。";
                break;
            case 2:
                msg = "服务暂不可用，请再次请求， 如果持续出现此类错误，请通过QQ群（224994340）或工单联系技术支持团队。";
                break;
            case 3:
                msg = "调用的API不存在，请检查后重新尝试";
                break;
            case 4:
                msg = "集群超限额";
                break;
            case 6:
                msg = "无权限访问该用户数据";
                break;
            case 17:
                msg = "每天请求量超限额";
                break;
            case 18:
                msg = "QPS超限额";
                break;
            case 19:
                msg = "请求总量超限额";
                break;
            case 100:
                msg = "无效的access_token参数，请检查后重新尝试";
                break;
            case 110:
                msg = "access_token无效";
                break;
            case 111:
                msg = "access token过期";
                break;
            case 282000:
                msg = "服务器内部错误，请再次请求， 如果持续出现此类错误，请通过QQ群（631977213）或工单联系技术支持团队。";
                break;
            case 216100:
                msg = "请求中包含非法参数，请检查后重新尝试";
                break;
            case 216101:
                msg = "缺少必须的参数，请检查参数是否有遗漏";
                break;
            case 216102:
                msg = "请求了不支持的服务，请检查调用的url";
                break;
            case 216103:
                msg = "请求中某些参数过长，请检查后重新尝试";
                break;
            case 216110:
                msg = "appid不存在，请重新核对信息是否为后台应用列表中的appid";
                break;
            case 216200:
                msg = "图片为空，请检查后重新尝试";
                break;
            case 216201:
                msg = "上传的图片格式错误，现阶段我们支持的图片格式为：PNG、JPG、JPEG、BMP，请进行转码或更换图片";
                break;
            case 216202:
                msg = "上传的图片大小错误，现阶段我们支持的图片大小为：base64编码后小于4M，分辨率不高于4096*4096，请重新上传图片";
                break;
            case 216630:
                msg = "识别错误，请再次请求，如果持续出现此类错误，请通过QQ群（631977213）或工单联系技术支持团队。";
                break;
            case 216631:
                msg = "识别银行卡错误，出现此问题的原因一般为：您上传的图片非银行卡正面，上传了异形卡的图片或上传的银行卡正品图片不完整";
                break;
            case 216633:
                msg = "识别身份证错误，出现此问题的原因一般为：您上传了非身份证图片或您上传的身份证图片不完整";
                break;
            case 216634:
                msg = "检测错误，请再次请求，如果持续出现此类错误，请通过QQ群（631977213）或工单联系技术支持团队。";
                break;
            case 282003:
                msg = "请求参数缺失";
                break;
            case 282005:
                msg = "处理批量任务时发生部分或全部错误，请根据具体错误码排查";
                break;
            case 282006:
                msg = "批量任务处理数量超出限制，请将任务数量减少到10或10以下";
                break;
            case 282114:
                msg = "URL长度超过1024字节或为0";
                break;
            case 282808:
                msg = "request id xxxxx 不存在";
                break;
            case 282809:
                msg = "返回结果请求错误（不属于excel或json）";
                break;
            case 282810:
                msg = "图像识别错误";
                break;
            default:
                msg = "未知错误";
                break;

        }

        return msg;
    }

    private static JSONObject convert(org.json.JSONObject jsonObject) {
        String s = jsonObject.toString();
        return JSON.parseObject(s);
    }

    /**
     * 接口返回的json转换为对象
     *
     * @param resultJson
     * @return
     * @throws OCRException
     */
    public static IdentityInfo getIdentityInfo(org.json.JSONObject resultJson) throws OCRException {
        JSONObject object = convert(resultJson);
        if (object.containsKey("error_code")) {
            Integer code = Integer.valueOf(object.get("error_code").toString());
            throw new OCRException(code, getErrorMsg(code));
        }
        logger.info(object.toJSONString());
        return getIdentityInfo(object);
    }

    private static IdentityInfo getIdentityInfo(JSONObject resultJson) {
        IdentityInfo info = new IdentityInfo();
        if (resultJson.containsKey("words_result")) {
            JSONObject wordsResultObj = resultJson.getJSONObject("words_result");

            if (wordsResultObj.containsKey("性别")) {
                JSONObject genderObj = wordsResultObj.getJSONObject("性别");
                String gender = genderObj.getString("words");
                info.setGender(gender);
            }
            if (wordsResultObj.containsKey("姓名")) {
                JSONObject nameObj = wordsResultObj.getJSONObject("姓名");
                String name = nameObj.getString("words");
                info.setName(name);
            }

            if (wordsResultObj.containsKey("住址")) {
                JSONObject addressObj = wordsResultObj.getJSONObject("住址");
                String address = addressObj.getString("words");
                info.setAddress(address);
            }
            if (wordsResultObj.containsKey("公民身份号码")) {
                JSONObject idcardObj = wordsResultObj.getJSONObject("公民身份号码");
                String idCard = idcardObj.getString("words");
                info.setIdCard(idCard);
            }

            if (wordsResultObj.containsKey("出生")) {
                JSONObject birthdayObj = wordsResultObj.getJSONObject("出生");
                String birthday = birthdayObj.getString("words");
                info.setBirthday(birthday);
            }

            if (wordsResultObj.containsKey("民族")) {
                JSONObject nationObj = wordsResultObj.getJSONObject("民族");
                String nation = nationObj.getString("words");
                info.setNation(nation);
            }

            if (wordsResultObj.containsKey("失效日期")) {
                JSONObject overdueTimeObj = wordsResultObj.getJSONObject("失效日期");
                String overdueTime = overdueTimeObj.getString("words");
                info.setOverdueTime(overdueTime);
            }

            if (wordsResultObj.containsKey("签发机关")) {
                JSONObject issuingAuthorityObj = wordsResultObj.getJSONObject("签发机关");
                String issuingAuthority = issuingAuthorityObj.getString("words");
                info.setIssuingAuthority(issuingAuthority);
            }

            if (wordsResultObj.containsKey("签发日期")) {
                JSONObject issuingTimeObj = wordsResultObj.getJSONObject("签发日期");
                String issuingTime = issuingTimeObj.getString("words");
                info.setIssuingTime(issuingTime);
            }

        }
        return info;
    }

    /**
     * 获取银行卡信息
     *
     * @param resultJson
     * @return
     * @throws OCRException
     */
    public static BankCardInfo getBankCardInfo(org.json.JSONObject resultJson) throws OCRException {
        JSONObject object = convert(resultJson);
        if (object.containsKey("error_code")) {
            Integer code = Integer.valueOf(object.get("error_code").toString());
            throw new OCRException(code, getErrorMsg(code));
        }
        logger.info(object.toJSONString());
        return getBankCardInfo(object);
    }

    private static BankCardInfo getBankCardInfo(JSONObject resultJson) {
        if (resultJson.containsKey("result")) {
            JSONObject result = resultJson.getJSONObject("result");

            String cardNumber = result.getString("bank_card_number");
            String bankName = result.getString("bank_name");
            Integer bankType = result.getInteger("bank_card_type");

            String cardType = bankType == 1 ? "储蓄卡" : "信用卡";
            BankCardInfo bankCardInfo = new BankCardInfo();
            bankCardInfo.setBankName(bankName);
            bankCardInfo.setCardNumber(cardNumber);
            bankCardInfo.setCardType(cardType);

            return bankCardInfo;
        }
        return null;
    }

}
