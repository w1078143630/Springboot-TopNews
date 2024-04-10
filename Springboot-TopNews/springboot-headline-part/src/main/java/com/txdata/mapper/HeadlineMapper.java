package com.txdata.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txdata.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txdata.pojo.vo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author www
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-04-09 10:58:58
* @Entity com.txdata.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {
    IPage<Map> selectMyPage(IPage page, @Param("portalVo") PortalVo portalVo);

    Map queryDetailMap(Integer hid);
}




