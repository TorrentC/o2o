package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;

import java.util.List;

public class ShopCategoryDto extends ShopCategory {
    private List<Shop> shopList;

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    @Override
    public String toString() {
        return super.toString() + "ShopCategoryDto{" +
                "shopList=" + shopList +
                '}';
    }
}
