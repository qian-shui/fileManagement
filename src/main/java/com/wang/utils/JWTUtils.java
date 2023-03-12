package com.wang.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import com.wang.pojo.User;
import com.wang.service.LoginService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    @Autowired
    private static LoginService loginService;

    private static final String jwtToken = "wang@#$$";

    public static String getToken(String userId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
                .setClaims(claims) // body数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));// 一天的有效时间
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static User findUserByToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)){
            try {
                Map<String, Object> map = checkToken(token);
                String userId = map.get("userId").toString();
                return loginService.getUserById(userId);
            }catch (Exception e){
                return null;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String token = getToken("str");
//        JWT.decode(token).getAudience.get(0); 另一个版本的jwt，可以获取到userId
        Jwt parse = Jwts.parser().parse(token);
        System.out.println(parse);
    }
}