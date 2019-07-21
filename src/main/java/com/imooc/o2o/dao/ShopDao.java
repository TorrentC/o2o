package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    int updateShop(Shop shop);
    int insertShop(Shop shop);
    Shop queryByShopId(Long shopId);

    /**
     *
     * @param shopCondition 根据店铺条件检索
     * @param rowIndex 从第几行返回
     * @param pageSize 放回行数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition, @Param("rowIndex")Integer rowIndex, @Param("pageSize") Integer pageSize);

    int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
