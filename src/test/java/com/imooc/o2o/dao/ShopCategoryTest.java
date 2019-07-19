package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryTest extends BaseTest {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testShopCategory() {

        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(1l);
        ShopCategory category = new ShopCategory();
        category.setParent(parent);
        List<ShopCategory> list = shopCategoryDao.queryShopCategory(category);
        for (ShopCategory shopCategory : list) {
            System.out.println(shopCategory.getShopCategoryName());
        }
    }
}
