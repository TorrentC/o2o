package com.imooc.o2o.service;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;

import java.io.File;
import java.io.InputStream;

public interface ShopService {
    ShopExecution addShop(Shop shop);

    /**
     * 通过shopId获取店铺
     * @param shopId
     * @return
     */
    Shop getByShopId(Long shopId);

    ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String filename);
}
