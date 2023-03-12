package com.wang.vo;

import com.wang.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

/*
* 接口统一返回包装类
* */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private boolean success;
    private String code;
    private String msg;
    private Object data;

    public static Result success(String msg,Object data){
        return new Result(true,Constants.CODE_200,msg,data);
    }

    public static Result fail(String code,String msg){
        return new Result(false,code,msg,null);
    }
}
