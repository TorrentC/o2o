package com.imooc.o2o.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtil {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    private static Random random = new Random();

    public static String getImgBasePath() {
        return "/home/torrent/image/";
    }

    public static String getShopImgPath(long shopId) {
        return "upload/item/shop/" + shopId + "/";
    }

    public static String getRandomFileName() {
        return format.format(new Date()) + (random.nextInt(9999) + 10000);
    }
}
