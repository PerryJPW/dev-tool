package com.perry.devtool.filter;

import com.perry.devtool.bo.RedisConnectionSettings;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Perry
 * @date 2021/2/14
 */
public class RedisInterceptor extends HandlerInterceptorAdapter  {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RedisConnectionSettings settings=RedisConnectionSettings.getInstance();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        if (!settings.getConnection()) {
            PrintWriter out = response.getWriter();
//            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) response).getWriter();
            out.write("{\n"
                    + "    \"state\": 10007,\n"
                    + "    \"msg\": \"连接异常\",\n"
                    + "    \"data\": null\n"
                    + "}");
            out.flush();
            out.close();
        } else {
            return true;
        }

        return false;
    }
}
