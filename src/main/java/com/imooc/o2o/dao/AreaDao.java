package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Area;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface AreaDao {
    @Select("select * from tb_area")
    List<Area> findAll();
}
