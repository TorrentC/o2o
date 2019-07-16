package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop() {

        Shop shop = new Shop();

        Area area = new Area();
        area.setAreaId(1);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1l);
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1l);

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);

        shop.setShopName("香飘飘奶茶店");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(0);
        shop.setShopAddr("address");
        shop.setShopDesc("奶茶店");

        ShopExecution shopExecution = shopService.addShop(null, new File("/home/torrent/IdeaProjects/o2o/src/main/resources/img/img.jpeg"));
        System.out.println(shopExecution.getStateInfo());
    }
}
