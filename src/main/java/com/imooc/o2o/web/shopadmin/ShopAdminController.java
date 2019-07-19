package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/shopadmin")
public class ShopAdminController {

    @RequestMapping("/shopoperation")
    public String shopOperate() {
        return "shop/shopoperation";
    }
}
