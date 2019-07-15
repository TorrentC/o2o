package com.imooc.o2o.web.superadmin;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;


@RequestMapping("/superadmin")
@Controller
public class AreaController {

    Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @RequestMapping("/listarea")
    @ResponseBody
    private HashMap<String, Object> listArea(@RequestParam(name = "id", required = false) Integer id) {
        logger.info("======start=======");

        Long start = System.currentTimeMillis();

        List<Area> all = areaService.findAll();
        HashMap<String, Object> map = new HashMap<>();
        map.put("all", all);
        map.put("id", id);
        System.out.println(all);

        Long end = System.currentTimeMillis();

        logger.debug("cost[{}]", end - start);

        logger.error("error test");
        logger.info("==========end========");
        return map;
    }
}

