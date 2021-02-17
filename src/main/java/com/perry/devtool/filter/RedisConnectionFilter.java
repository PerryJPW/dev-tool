package com.perry.devtool.filter;

import com.perry.devtool.bo.RedisConnectionSettings;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Perry
 * @date 2021/2/14
 */
public class RedisConnectionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpSession session = ((HttpServletRequest) request).getSession();

        RedisConnectionSettings settings=RedisConnectionSettings.getInstance();
//        log.info("Login-IP:" + ((HttpServletRequest) request).getRemoteUser());
        //登录校验
//        System.out.println(response.getCharacterEncoding());
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
            chain.doFilter(request, response);
        }
        //管理员校验

    }

    @Override
    public void destroy() {

    }
}
