package com.imooc.o2o.util;

import com.imooc.o2o.web.superadmin.AreaController;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImgUtil {

    private final static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private final static Logger logger = LoggerFactory.getLogger(ImgUtil.class);

    /**
     * 创建缩略图
     * return 相对路径
     */
    public static String generateThumbnail(InputStream thumbnailInputStream, String filename, String targetAddr) {
        //1.获取随机文件名
        String randomFileName = FileUtil.getRandomFileName();
        //2.获取文件拓展
        String extension = getFileExtension(filename);
        //3.创建目录
        mkdir(targetAddr);

        //获取相对路径 绝对路径
        String relativePath = targetAddr + randomFileName + extension;

        logger.info("thumbnail relative path is {}", relativePath);

        String realPath = FileUtil.getImgBasePath() + relativePath;
        logger.info("thumbnail real path is {}", realPath);

        try {
            Thumbnails.of(thumbnailInputStream)
                    // 压缩后图片大小
                    .size(318, 450)
                    // 水印图片
                    .watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(basePath + "img/watermark.png")), 1f)
                    // 图片压缩比
                    .outputQuality(0.8f)
                    // 压缩后图片位置
                    .toFile(realPath);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return relativePath;
    }


    private static void mkdir(String targetAddr) {


        String realPath = FileUtil.getImgBasePath() + targetAddr;
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
            logger.info("mkdir {}", realPath);
        }
    }

    private static String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }


    public static void main(String[] args) throws IOException {
//        System.out.println(basePath);
//
//        Thumbnails.of(new File(basePath + "img/img.jpeg"))
//                // 压缩后图片大小
//                .size(318, 450)
//                // 水印图片
//                .watermark(Positions.BOTTOM_RIGHT,
//                        ImageIO.read(new File(basePath + "img/watermark.png")), 1f)
//                // 图片压缩比
//                .outputQuality(0.8f)
//                // 压缩后图片位置
//                .toFile("src/main/resources/img/002New.png");

        String s = generateThumbnail(new FileInputStream("/home/torrent/IdeaProjects/o2o/src/main/resources/img/img.jpeg"), "img.jpeg", FileUtil.getShopImgPath(1l));
        System.out.println(s);
    }

    public static void deletePathOrFile(String storePath) {
        File fileOrPath = new File(FileUtil.getImgBasePath() + storePath);
        //不存在直接返回
        if (!fileOrPath.exists()) {
            return;
        }

        //如果是目录 删除下面的文件
        if (fileOrPath.isDirectory()) {
            File[] listFiles = fileOrPath.listFiles();
            for (File listFile : listFiles) {
                listFile.delete();
            }
        }

        fileOrPath.delete();
    }
}
