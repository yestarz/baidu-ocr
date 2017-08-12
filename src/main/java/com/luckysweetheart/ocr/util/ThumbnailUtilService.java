package com.luckysweetheart.ocr.util;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 压缩图片
 * Created by yangxin on 2017/8/11.
 */
@Service
public class ThumbnailUtilService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int WIDTH = 450;

    public byte[] createThumbnail(byte[] sourceFile) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(sourceFile);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(inputStream).size(WIDTH, getHeight(sourceFile)).toOutputStream(outputStream);

        byte[] bytes = outputStream.toByteArray();
        inputStream.close();
        outputStream.close();
        return bytes;
    }

    private int getHeight(byte[] bytes) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage buff = ImageIO.read(inputStream);

        int width = buff.getWidth(); // 得到图片的宽度
        int height = buff.getHeight(); // 得到图片的高度

        inputStream.close();

        if (width < WIDTH) {
            return height;
        }

        double a = (double) width / (double) height;

        height = (int) (WIDTH / a);

        return height;
    }
}
