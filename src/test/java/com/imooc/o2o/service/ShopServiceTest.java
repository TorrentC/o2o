package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopDao shopDao;

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

    }

    @Test
    public void modifyShopTest() throws FileNotFoundException {

        Shop shop = shopDao.queryByShopId(4l);

        ShopExecution shopExecution = shopService.modifyShop(shop, new FileInputStream("/home/torrent/桌面/1.jpg"), "1.jpg");
        System.out.println(shopExecution);
    }
}
