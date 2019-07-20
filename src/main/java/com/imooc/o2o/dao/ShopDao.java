package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {
    int updateShop(Shop shop);
    int insertShop(Shop shop);
    Shop queryByShopId(Long shopId);
}
