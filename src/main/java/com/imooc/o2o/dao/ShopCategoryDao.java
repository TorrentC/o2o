package com.imooc.o2o.dao;

import com.imooc.o2o.dto.ShopCategoryDto;
import com.imooc.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);

    List<ShopCategoryDto> queryAllShopByCategoryId(Long ShopCategoryId);
}
