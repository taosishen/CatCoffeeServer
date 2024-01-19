package com.taosisheng.filter;

import com.alibaba.fastjson.JSON;
import com.taosisheng.common.BaseContext;
import com.taosisheng.dtoentity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * 检查用户是否已经登录
 * */


//urlPatterns="/*"；拦截所有路径
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    @ResponseBody
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        //1.获取本次请求的URL
        String requestUrl = request.getRequestURI();
        log.info("拦截到请求：{}", requestUrl);
        //定义不需要处理的请求路径
        String[] urls = new String[]{"/staff/login", "/staff/logout", "/user/register", "/user/login", "/user/logout", "/favicon.ico", "/file/download"};
        boolean check = check(urls, requestUrl);

        if (requestUrl == "/favicon.ico") return;
        //2.判断本次请求是否需要处理
        if (check) {
            log.info("本次请求用户{}不需要处理", requestUrl);
            filterChain.doFilter(request, response);
            return;
        }
        //4.判断登录状态，已登录放行
        if (request.getSession().getAttribute("staff") != null) {
            log.info("员工已登录", requestUrl);
            Long stfId = (Long) request.getSession().getAttribute("staff");
            BaseContext.setCurrentId(stfId);
            log.info(request.getSession().getAttribute("staff").toString());

            long id = Thread.currentThread().getId();
            log.info("线程id为:{}", id);

            filterChain.doFilter(request, response);
            return;
        }
        if (request.getSession().getAttribute("user") != null) {
            //通过过滤器的路径
            String[] userUrls = new String[]{"/goods/getGoodById", "/goods/getClassList/", "/goods/selectByclass/",
                    "/cart/getAllCart", "/cart/buy", "/cart/removeCart", "/cart/pushCart", "/goods/getGoodById",
                    "/user/login", "/user/register", "/user/getUserMsg", "/order/getOrderNum", "/order/select",
                    "/user/recharge", "/order/getOrderMsg", "/order/getOrder", "/cart/changeCart", "/cart/getAllCart",
                    "/pages/details/details", "/goods/selectByclass/", "/goods/getClassList/", "/pages/buy/buy",
                    "/file/downloadGoods","/file/downloadAvatar","/file/uploadGoods","/file/changeAvatar"};
            boolean userCheck = check(userUrls, requestUrl);
            if (!userCheck){
                response.getWriter().write(JSON.toJSONString(R.error("无权限！！！", 400)));
                return;
            }
            log.info("普通用户已登录,鉴权完成", requestUrl);
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            log.info(request.getSession().getAttribute("user").toString());

            long id = Thread.currentThread().getId();
            log.info("线程id为:{}", id);

            filterChain.doFilter(request, response);
            return;
        }
//        filterChain.doFilter(request, response);

//        5.如果未登录则返回未登录结果,通过输出流方式向客户端发送数据

        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("未登录", 400)));
    }

    public boolean check(String[] urls, String requestURL) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
