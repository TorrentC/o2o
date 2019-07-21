package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@RequestMapping("/shopadmin")
public class ShopAdminController {

    @RequestMapping("/shopoperation")
    public String shopOperate() {
        return "shop/shopoperation";
    }

    @RequestMapping("/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }

    @RequestMapping("/shopmanagement")
    public String shopManage() {
        return "shop/shopmanagement";
    }
}
