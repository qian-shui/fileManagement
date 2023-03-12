package com.wang.handler.interceptor;

import cn.hutool.core.util.StrUtil;
import com.wang.common.Constants;
import com.wang.config.AuthAccess;
import com.wang.exception.ServiceException;
import com.wang.pojo.User;
import com.wang.service.LoginService;
import com.wang.service.UserService;
import com.wang.utils.JWTUtils;
import io.jsonwebtoken.Jwts;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if(!(handler instanceof HandlerMethod)){
            return true;
        }else{
            //判断此接口是否带上AuthAccess注解，如果有则不拦截
            HandlerMethod h = (HandlerMethod) handler;
            AuthAccess methodAnnotation = h.getMethodAnnotation(AuthAccess.class);
            if(methodAnnotation != null){
                return true;
            }
        }

        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token，请登录");
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        User user;
        try {
            user = loginService.getUserById(map.get("userId").toString());
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if(user == null){
            throw new ServiceException(Constants.CODE_401,"用户不存在");
        }
        return true;
    }
}
