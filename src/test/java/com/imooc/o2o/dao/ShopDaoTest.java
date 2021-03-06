package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    /**
     * 根据条件, 分页查询
     */
    @Test
    public void queryShopListTest() {

        Shop shop1 = new Shop();
        shop1.setShopName("测");

        List<Shop> shops = shopDao.queryShopList(shop1, 0, 5);
        for (Shop shop : shops) {
            System.out.println(shop);
        }

//        int i = shopDao.queryShopCount(shop1);
//        System.out.println(i);
    }

    @Test
    public void queryByShopIdTest() {
        Shop shop = shopDao.queryByShopId(4l);
        System.out.println(shop);
    }

    @Test
    public void insertShopTest() {


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

        int insertShop = shopDao.insertShop(shop);
        System.out.println(insertShop);
        System.out.println(shop.getShopId());
    }

    @Test
    public void updateShopTest() {


        Shop shop = new Shop();
        shop.setShopId(4l);


        shop.setShopName("优乐美奶茶店");
        shop.setLastEditTime(new Date());
        shop.setPhone("110");

        int insertShop = shopDao.updateShop(shop);
        System.out.println(insertShop);
        System.out.println(shop.getShopId());
    }
}
