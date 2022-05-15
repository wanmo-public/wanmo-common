package cn.com.jeeweb.common.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * 图片文件工具类
 */
public class ImageUtil {

    /**
     * <p>Discription:[getImageFileType,获取图片文件实际类型,若不是图片则返回null]</p>
     * @param file
     * @return fileType
     */
    public final static String getImageFileType(File file)
    {
        if (isImage(file)) {
            try {
                ImageInputStream iis = ImageIO.createImageInputStream(file);
                Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
                if (!iter.hasNext()) {
                    return null;
                }
                ImageReader reader = iter.next();
                iis.close();
                return reader.getFormatName();
            }
            catch (IOException e) {
                return null;
            }
            catch (Exception e) {
                return null;
            }
        }
        return null;
    }


    /**
     * <p>Discription:[isImage,判断文件是否为图片]</p>
     * @param file
     * @return true 是 | false 否
     */
    public static final boolean isImage(File file){
        boolean flag = false;
        try {
            BufferedImage bufreader = ImageIO.read(file);
            int width = bufreader.getWidth();
            int height = bufreader.getHeight();
            if(width==0 || height==0){
                flag = false;
            }else {
                flag = true;
            }
        }
        catch (IOException e) {
            flag = false;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
