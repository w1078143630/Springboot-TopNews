package com.txdata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txdata.pojo.Type;
import com.txdata.service.TypeService;
import com.txdata.mapper.TypeMapper;
import com.txdata.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author www
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-04-09 10:58:58
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private TypeMapper typeMapper;
    @Override
    public Result findAllTypes() {

        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);
    }
}




