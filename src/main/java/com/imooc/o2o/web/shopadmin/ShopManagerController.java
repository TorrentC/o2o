package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Constants;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
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

        //获取不了验证码

        String codeActual = request.getParameter("verifyCodeActual");
        System.out.println(codeActual);
        //验证码测试是否通过
        boolean pass = CodeUtil.compare(request);
        if (!pass) {
            model.put("success", false);
            model.put("errMsg", "验证码错误");
            return model;
        }

        model.put("success", true);

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
