package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperateException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.FileUtil;
import com.imooc.o2o.util.ImgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImp implements ShopService {

    @Autowired
    private ShopDao shopDao;

    Logger logger = LoggerFactory.getLogger(ShopService.class);


    @Override
    @Transactional
    public ShopExecution addShop(Shop shop) {

        //1 判断shop user以及userId是否存在
        if (shop == null || shop.getOwner() ==null) {
            return new ShopExecution(ShopStateEnum.INNER_ERROR);
        }

        //2 初始化shop的基本信息
        shop.setEnableStatus(0);
        shop.setCreateTime(new Date());

        //3 插入数据库
        try {
            int i = shopDao.insertShop(shop);
            if (i != 1) {
                throw new ShopOperateException("创建店铺失败");
            }
        } catch (Exception e) {
            throw new ShopOperateException(e.getMessage());
        }


        return new ShopExecution(ShopStateEnum.CHECK);
    }

    @Override
    public Shop getByShopId(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    /**
     *
     * @param shop 来自controller 有用户提交
     * @param shopImgInputStream
     * @param filename
     * @return
     */
    @Override
    @Transactional
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String filename) {
        try {
            //1 判断店铺和店铺Id是否为null
            if (shop == null || shop.getShopId() == null) {
                logger.error("shop or shopId is null");
                return new ShopExecution(ShopStateEnum.NULL_SHOP_ID);
            }

            //2 判断shopImgInputStream和filename是否为null 然后更新Shop的shopImg属性
            if (shopImgInputStream != null && filename != null && !"".equals(filename)) {
                //通过shopId从数据库中拿到对应信息
                Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                String shopImg = tempShop.getShopImg();
                if (shopImg != null) {
                    logger.info("shopImg is delete");
                    ImgUtil.deletePathOrFile(filename);
                }

                logger.info("shopImg is update");
                addShopImg(shop, shopImgInputStream, filename);

            }


            //3 最后修改店铺信息
            logger.info("shop information is update");
            int i = shopDao.updateShop(shop);
            if (i == 1) {
                return new ShopExecution(ShopStateEnum.SUCCESS, shop);
            }

            return new ShopExecution(ShopStateEnum.INNER_ERROR);
        } catch (Exception e) {
            throw new ShopOperateException("modify error:" + e.getMessage());
        }
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream, String filename) {
        String shopImgPath = FileUtil.getShopImgPath(shop.getShopId());
        String path = ImgUtil.generateThumbnail(shopImgInputStream, filename, shopImgPath);
        shop.setShopImg(path);
    }


}
