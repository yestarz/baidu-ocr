import com.baidu.aip.ocr.AipOcr;
import com.luckysweetheart.ocr.dto.IdentityInfo;
import com.luckysweetheart.ocr.exception.OCRException;
import com.luckysweetheart.ocr.service.BaiduOCRService;
import com.luckysweetheart.ocr.util.ThumbnailUtilService;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * Created by yangxin on 2017/8/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/spring-context.xml"})
public class OCRTest {

    String imagePath = "C:\\Users\\dp\\Desktop\\证件识别\\IMG_4541.JPG";
    //String imagePath = "C:\\Users\\dp\\Desktop\\lucky-web.png";

    String bankImg = "C:\\Users\\dp\\Desktop\\证件识别\\IMG_4536.JPG";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AipOcr aipOcr;

    @Resource
    private ThumbnailUtilService thumbnailUtilService;

    @Resource
    private BaiduOCRService baiduOCRService;

    @Test
    public void test1() throws IOException, OCRException {
        byte[] bytes = FileUtils.readFileToByteArray(new File(imagePath));
        IdentityInfo identityInfo = baiduOCRService.getIdentityInfo(bytes,true);
        System.out.println(identityInfo);
    }

    @Test
    public void test2() throws Exception{
        byte[] bytes = FileUtils.readFileToByteArray(new File(bankImg));
        System.out.println(baiduOCRService.getBankCardInfo(bytes));
    }

}
