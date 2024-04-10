package com.txdata.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.txdata.utils.JwtHelper;
import com.txdata.utils.Result;
import com.txdata.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ClassName: LoginProtectedInterceptor
 * Description:登陆保护拦截器，检查请求头是否包含有效token
 *  有，有效，放行
 *  没有，无效，返回504
 * @Author Wei Wang
 * @Create 2024/4/10 9:31
 * @Version 1.0
 */
@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        boolean expiration = jwtHelper.isExpiration(token);
        if (!expiration){
            return true;
        }
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);

        return false;
    }
}
