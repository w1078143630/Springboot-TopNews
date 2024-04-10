package com.txdata.service;

import com.txdata.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txdata.utils.Result;

/**
* @author www
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-04-09 10:58:58
*/
public interface TypeService extends IService<Type> {

    Result findAllTypes();
}
