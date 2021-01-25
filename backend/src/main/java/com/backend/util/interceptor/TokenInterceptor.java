package com.backend.util.interceptor;

import com.backend.util.token.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws IOException {
        if(StringUtils.equals("OPTIONS", request.getMethod())){
            return true;
        }
        String token = request.getHeader("Authorization");
        String userName = TokenUtil.verify(token);
        if (StringUtils.isNoneEmpty(userName)) {
            request.setAttribute("userName", userName);
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try{
            JSONObject json = new JSONObject();
            json.put("success",false);
            json.put("errorMessage","token verify fail");
            json.put("errorCode","410");
            response.getWriter().append(json.toString());
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(400);
            return false;
        }
        return false;
    }
}
