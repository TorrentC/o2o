package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopCategoryDto;
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

    @Test
    public void testQueryShop() {
        List<ShopCategoryDto> shopCategoryDto = shopCategoryDao.queryAllShopByCategoryId(1l);
        for (int i = 0; i < shopCategoryDto.size(); i++) {
            System.out.println(shopCategoryDto.get(i));
        }
    }
}
