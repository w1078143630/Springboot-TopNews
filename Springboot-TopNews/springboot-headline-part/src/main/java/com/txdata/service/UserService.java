package com.txdata.service;

import com.txdata.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txdata.utils.Result;

/**
* @author www
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-04-09 10:58:58
*/
public interface UserService extends IService<User> {

    /**
     * 登录业务
     * @param user
     * @return
     */
    Result login(User user);

    Result getUserInfo(String token);

    //检查账号是否可用
    Result checkUserName(String username);

    Result regist(User user);
}
