package com.zhunzhong.demo.mysql.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhunzhong.demo.mysql.entity.Meter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface MeterDao extends BaseMapper<Meter> {

}
