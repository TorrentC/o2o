package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Constants;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperateException;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.FileUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagerController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @RequestMapping("/getshoplist")
    @ResponseBody
    public Map<String, Object> getShopList() {

        Map<String, Object> model = new HashMap<>();
        //从session中获取user信息
        PersonInfo user = new PersonInfo();
        user.setUserId(1l);
        user.setName("唐嫣");
        Shop shop = new Shop();
        shop.setOwner(user);

        try {
            ShopExecution shopExecution = shopService.getShopList(shop, 0, 200);

            if (shopExecution.getState() != ShopStateEnum.SUCCESS.getState()) {
                model.put("success", false);
                model.put("errMsg", shopExecution.getStateInfo());
                return model;
            }

            model.put("shopList", shopExecution.getShopList());
            model.put("user", user);
            model.put("success", true);

            return model;
        } catch (Exception e) {
            model.put("success", false);
            model.put("errMsg", e.getMessage());
            return model;
        }

    }

    @RequestMapping("/getshopbyid")
    @ResponseBody
    public Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");

        if (shopId < 0) {
            model.put("success", false);
            model.put("errMsg", ShopStateEnum.NULL_SHOP_INFO.getStateInfo());
            return model;
        }

        try {
            Shop shop = shopService.getByShopId(shopId);
            List<Area> areaList = areaService.findAll();
            model.put("shop", shop);
            model.put("areaList", areaList);
            model.put("success", true);
        } catch (Exception e) {
            model.put("success", false);
            model.put("errMsg", e.getMessage());
        }


        return model;
    }

    @RequestMapping("/getshopinitinfo")
    @ResponseBody
    public Map<String, Object> getShopInitInfo() {
        HashMap<String, Object> model = new HashMap<>();
        try {
            List<ShopCategory> shopCategories = shopCategoryService.getShopCategoryList(new ShopCategory());
            List<Area> areas = areaService.findAll();

            model.put("success", true);
            model.put("shopCategoryList", shopCategories);
            model.put("areaList", areas);

        } catch (Exception e) {
            model.put("success", false);
            model.put("errMsg", e.getMessage());
        }
        return model;
    }

    @RequestMapping(path = "/registershop", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> register(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();


        //验证码测试是否通过
        boolean pass = CodeUtil.compare(request);
        if (!pass) {
            model.put("success", false);
            model.put("errMsg", "验证码错误");
            return model;
        }

        //获取店铺相关信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        //将json信息封装成一个shop对象
        ObjectMapper mapper = new ObjectMapper();

        Shop shop = null;

        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            model.put("success", false);
            model.put("errMsg", e.getMessage());
            return model;
        }

        //将表单数据写入数据库

        // 1.判断shop数据是否为空
        if (shop == null || shop.getShopName() == null) {
            model.put("success", false);
            model.put("errMsg", "用户名不能为空");
            return model;
        }

        // 2.从session中获取用户userId

        PersonInfo owner = new PersonInfo();
        owner.setUserId(1l);

        shop.setOwner(owner);

        // 3.添加到数据库
        ShopExecution shopExecution = shopService.addShop(shop);

        if (shopExecution.getState() != ShopStateEnum.CHECK.getState()) {
            model.put("success", false);
            model.put("errMsg", shopExecution.getStateInfo());
            return model;
        }

        //获取上传图片
        MultipartHttpServletRequest multipartHttpServletRequest = null;
        MultipartFile shopImg = null;
        MultipartResolver  mutipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
                // 判断request 是否有文件上传请求
        if(mutipartResolver.isMultipart(request)) {
            multipartHttpServletRequest = (MultipartHttpServletRequest)request;
                // 获取request中上传文件中的shopImg文件
            shopImg = multipartHttpServletRequest.getFile("shopImg");
        }

        //将shopImg信息写入到数据库
        if (shopImg != null) {
            try {
                //不符合预期 抛出异常
                ShopExecution modifyShop = shopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                if (modifyShop.getState() != ShopStateEnum.SUCCESS.getState()) {
                    throw new ShopOperateException("图片上传异常");
                }
            } catch (ShopOperateException | IOException e) {
                model.put("success", false);
                model.put("errMsg", e.getMessage());
                return model;
            }
        }


        model.put("success", true);

        return model;
    }

    @RequestMapping(value = "/updateshop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateShop() {
        return null;
    }


    @RequestMapping("/getshopmanagementinfo")
    @ResponseBody
    public Map<String, Object> getShopManageInfo(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        //从session中获取当前店铺信息
        Shop currentShop = new Shop();
        currentShop.setShopId(6l);

        long shopId = HttpServletRequestUtil.getLong(request, "shopId");

        if (shopId > 0) {

            model.put("redirect", false);
            model.put("shopId", currentShop.getShopId());

        } else {
            if(currentShop == null) {
                model.put("redirect", true);
                model.put("url", "o2o/shopadmin/shoplist");
            }
            else {
                model.put("redirect", false);
                model.put("shopId", currentShop.getShopId());
            }

        }

        return model;
    }


    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(parameterMap.size());
        Set<String> strings = parameterMap.keySet();
        for (String string : strings) {
            System.out.println(parameterMap.get(string)[0]);
        }
        return "ok";
    }
}
