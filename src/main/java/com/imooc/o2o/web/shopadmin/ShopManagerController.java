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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

@Controller
@RequestMapping("/shopadmin")
public class ShopManagerController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

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

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request, Shop shop) {

        //验证码测试是否通过
        boolean pass = CodeUtil.compare(request);

        if (!pass) {
            return "error";
        }

        //在session中拿到用户信息
        PersonInfo user = new PersonInfo();
        user.setUserId(1l);

        shop.setOwner(user);

        ShopExecution shopExecution = shopService.addShop(shop);
        if (shopExecution.getState() != 1) {
            return "error";
        }


        return "shop/upload";
    }

    @ResponseBody
    @RequestMapping("/upload")
    public String upload(HttpServletRequest request) {
//        MultipartHttpServletRequest multipartHttpServletRequest = null;
//        MultipartFile shopImg = null;
//        MultipartResolver mutipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        // 判断req 是否有文件上传请求
//        if (mutipartResolver.isMultipart(request)) {
//            multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//            // 获取req中上传文件中的shopImg文件
//            shopImg = multipartHttpServletRequest.getFile("shopImg");
//        }

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        if (resolver.isMultipart(request)) {
            MultipartFile shopImg = multipartRequest.getFile("shopImg");

            System.out.println(shopImg);
            String filename = shopImg.getOriginalFilename();
            System.out.println(filename);
        }

        return "upload";
    }

    @RequestMapping("/test")
    public String test() {
        return "shop/upload";
    }

    @ResponseBody
    @RequestMapping(path = "/registershop1", method = RequestMethod.POST)
    public Map<String, Object> registerShop(HttpServletRequest request) {
        HashMap<String, Object> model = new HashMap<>();

        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");

        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;

        //封装注册店铺基本信息
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            model.put("success", false);
            model.put("errMsg", e.getMessage());
            return model;
        }

        //从session中获取注册人信息
        PersonInfo person = new PersonInfo();
        person.setUserId(1l);

        shop.setOwner(person);

        //获取用户上传图片
        MultipartHttpServletRequest multipartHttpServletRequest = null;
        MultipartFile shopImg = null;
        MultipartResolver mutipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 判断req 是否有文件上传请求
        if (mutipartResolver.isMultipart(request)) {
            multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            // 获取req中上传文件中的shopImg文件
            shopImg = multipartHttpServletRequest.getFile("shopImg");
        }
        if (shopImg == null) {
            model.put("success", false);
            model.put("errMsg", "没有上传图片");
            return model;
        }

        //注册店铺
        try {
            File file = FileUtil.multipartFileToFile(shopImg);
            ShopExecution execution = shopService.addShop(shop, file);

            if (execution.getState() == ShopStateEnum.CHECK.getState()) {
                model.put("success", true);
                model.put("msg", ShopStateEnum.CHECK.getStateInfo());
                return model;
            } else {
                model.put("success", false);
                model.put("errMsg", "注册店铺数据库失败");
                return model;
            }

        } catch (IOException e) {
            model.put("success", false);
            model.put("errMsg", "文件转换异常");
            return model;
        }

    }
}
