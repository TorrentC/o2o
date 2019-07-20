## 这是一个关于ssm项目的实战案例

##写了商铺注册的功能(未完成)

个人总结
1. 关于mybatis
resultMap id标签至关重要

2.文件上传没有配置相关类

3. 明天实现商铺添加和更新的功能


select s.shop_name, s.shop_id, sc.shop_category_name, **sc.shop_category_id**
        from tb_shop_category sc join tb_shop s
        on s.shop_category_id = sc.shop_category_id
        where sc.shop_category_id=#{ShopCategoryId}
        
 shopList=[Shop{shopId=4, shopName='优乐美奶茶店'},
  Shop{shopId=5, shopName='香飘飘奶茶店'}  
  
  select s.shop_name, s.shop_id, sc.shop_category_name
          from tb_shop_category sc join tb_shop s
          on s.shop_category_id = sc.shop_category_id
          where sc.shop_category_id=#{ShopCategoryId} 

ShopCategory{ shopCategoryD{shopList=[Shop{shopId=4, shopName='优乐美奶茶店', shopDesc='null', shopAddr='null', phone='null', shopImg='null', priority='null', createTime=null, lastEditTime=null, enableStatus=null, advice='null', area=null, owner=null, shopCategory=null}]}
ShopCategory{ shopCategoshopList=[Shop{shopId=5, shopName='香飘飘奶茶店', shopDesc='null', shopAddr='null', phone='null', shopImg='null', priority='null', createTime=null, lastEditTime=null, enableStatus=null, advice='null', area=null, owner=null, shopCategory=null}]}
ShopCategory{shopList=[Shop{shopId=6, shopName='测试', shopDesc='null', shopAddr='null', phone='null', shopImg='null', priority='null', createTime=null, lastEditTime=null, enableStatus=null, advice='null', area=null, owner=null, shopCategory=null}]}
ShopCategory{shopList=[Shop{shopId=7, shopName='测试', shopDesc='null', shopAddr='null', phone='null', shopImg='null', prior   

```
<resultMap id="shopCategoryMap" type="com.imooc.o2o.dto.ShopCategoryDto">
        <id property="shopCategoryId" column="shop_category_id"/>
        <result property="shopCategoryName" column="shop_category_name"/>
        <collection property="shopList" ofType="com.imooc.o2o.entity.Shop">
            <id property="shopId" column="shop_id"/>
            <result column="shop_name" property="shopName"/>
        </collection>
    </resultMap>

    <select id="queryAllShopByCategoryId" resultMap="shopCategoryMap" parameterType="long">
        select s.shop_name, s.shop_id, sc.shop_category_name
        from tb_shop_category sc join tb_shop s
        on s.shop_category_id = sc.shop_category_id
        where sc.shop_category_id=#{ShopCategoryId}
    </select>
```

```
 <!-- 文件解析类 -->
    <bean id="multipartResolver" 
         class="org.springframework.web.multipart.commons.CommonsMultipartResolver"> 
        <property name="defaultEncoding" value="utf-8"/> 
        <!-- 1024*1024*20 = 20M -->
        <property name="maxUploadSize" value="20971520"/>
        
		<property name="maxInMemorySize" value="20971520"/> 

    </bean> 
```