package com.txdata.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import com.txdata.pojo.User;
import com.txdata.service.UserService;
import com.txdata.mapper.UserMapper;
import com.txdata.utils.JwtHelper;
import com.txdata.utils.MD5Util;
import com.txdata.utils.Result;
import com.txdata.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author www
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-04-09 10:58:58
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result login(User user) {

        //根据账号查询
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);

        if(loginUser == null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //对比密码
        if(!StringUtils.isEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals((loginUser.getUserPwd()))){
            //登录成功
            //根据用户id生产token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));

            //token封装到result返回
            Map data = new HashMap();
            data.put("token",token);
            return Result.ok(data);


        }

        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result getUserInfo(String token) {

        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration) {
            //失效，未登录看待
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(token).intValue();

        User user = userMapper.selectById(userId);
        user.setUserPwd("");

        Map data = new HashMap();
        data.put("loginUser",user);

        return Result.ok(data);
    }

    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> queryWrapper
                = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        long count = userMapper.selectCount(queryWrapper);

        if (count == 0) {
            return Result.ok(null);
        }
        return Result.build(null,ResultCodeEnum.USERNAME_USED);

    }

    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper
                = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        long count = userMapper.selectCount(queryWrapper);

        if (count > 0) {
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));//密码加密
        userMapper.insert(user);
        return Result.ok(null);
    }


}




