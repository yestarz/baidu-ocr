import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by yangxin on 2017/8/12.
 */
public class ThumbnailTest {

    private static Logger logger = LoggerFactory.getLogger(ThumbnailTest.class);

    public static void main(String[] args) {
        try {
            byte[] bytes = FileUtils.readFileToByteArray(new File("C:\\Users\\dp\\Desktop\\证件识别\\888.JPG"));

            InputStream inputStream = new ByteArrayInputStream(bytes);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Thumbnails.of(inputStream).size(200, convert(bytes)).toOutputStream(outputStream);

            byte[] file = outputStream.toByteArray();

            FileUtils.writeByteArrayToFile(new File("C:\\Users\\dp\\Desktop\\证件识别\\111.jpg"),file);

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void test1() throws IOException {
        byte[] bytes = FileUtils.readFileToByteArray(new File("C:\\Users\\dp\\Desktop\\证件识别\\IMG_4532.JPG"));
        System.out.println(((double)bytes.length) / 1024 / 1024);
    }

    public void test2(){
        System.out.println(200 * 1024 * 1024);
    }

    public static int[] getWH(byte[] bytes) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage buff = ImageIO.read(inputStream);

        inputStream.close();

        int width = buff.getWidth(); // 得到图片的宽度
        int height = buff.getHeight(); // 得到图片的高度

        return new int[]{width,height};
    }

    public static int convert(byte[] bytes) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage buff = ImageIO.read(inputStream);

        int width = buff.getWidth(); // 得到图片的宽度
        int height = buff.getHeight(); // 得到图片的高度

        inputStream.close();

        if(width < 500){
            return height;
        }

        double a = (double) width / (double) height;

        height = (int) (500 / a);

        return height;
    }

}
