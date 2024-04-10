package com.txdata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txdata.pojo.Headline;
import com.txdata.pojo.vo.PortalVo;
import com.txdata.service.HeadlineService;
import com.txdata.mapper.HeadlineMapper;
import com.txdata.utils.JwtHelper;
import com.txdata.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author www
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-04-09 10:58:58
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private HeadlineMapper headlineMapper;
    @Autowired
    private JwtHelper jwtHelper;


    /**
     * 首页数据查询
     * 1.分页查询
     * 2.分页数据拼接
     * @param portalVo
     * @return
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {

        IPage<Map> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
        headlineMapper.selectMyPage(page,portalVo);

        List<Map> records = page.getRecords();
        Map data = new HashMap();
        data.put("pageData",records);
        data.put("pageNum",page.getCurrent());
        data.put("pageSize",page.getSize());
        data.put("toalPage",page.getPages());
        data.put("totalSize",page.getTotal());

        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo",data);

        return Result.ok(pageInfo);
    }


    /**
     * 根据id查询详情
     * 2.查询对应的数据
     * 1.修改阅读量+1
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {

        Map data = headlineMapper.queryDetailMap(hid);
        Map headlineMap = new HashMap();
        headlineMap.put("headline",data);

        //阅读量
        Headline headline = new Headline();
        headline.setHid((Integer) data.get("hid"));
        headline.setVersion((Integer) data.get("version"));

        headline.setPageViews((Integer) data.get("pageViews") +1);

        headlineMapper.updateById(headline);
        return Result.ok(headlineMap);
    }

    /**
     * 发布头条
     *1.补全数据
     *
     * @return
     */
    @Override
    public Result publish(Headline headline, String token) {
        //查询用户id
        int userId = jwtHelper.getUserId(token).intValue();
        //数据装配
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);

        return Result.ok(null);
    }

    /**
     * 修改头条数据
     * 1.hid查询数据的最新version
     * 2.修改数据的修改时间为当前节点
     * @param headline
     * @return
     */
    @Override
    public Result updateData(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();
        headline.setVersion(version);//乐观锁
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);

        return Result.ok(null);
    }


}




