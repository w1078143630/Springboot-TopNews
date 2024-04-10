package com.txdata.service;

import com.txdata.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txdata.pojo.vo.PortalVo;
import com.txdata.utils.Result;

/**
* @author www
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-04-09 10:58:58
*/
public interface HeadlineService extends IService<Headline> {

    /**
     * 首页数据查询
     * @param portalVo
     * @return
     */
    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline, String token);

    Result updateData(Headline headline);
}
