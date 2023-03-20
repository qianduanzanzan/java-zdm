package com.javazdm.handler;

import com.javazdm.entity.User;
import com.javazdm.mapper.UserMapper;
//import com.javazdm.utils.TokenUtil;
import com.javazdm.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取请求头信息
        String token = request.getHeader("token");
        System.out.println(token);
        //如果U-TOKEN为空则证明没有登录
        if (StringUtils.isEmpty(token)) {
            writeJosn(response, "用户未登录", 52000);
            //拦截
            return false;
        }
        Integer userId = tokenUtil.getIdFromToken(token);

        User user = userMapper.selectById(userId);
        String updateAt = tokenUtil.getUpdateAtFromToken(token);
        if (StringUtils.isEmpty(user) || !updateAt.equals(user.getUpdateAt().toString())) {
            redisTemplate.delete(token);
            writeJosn(response, "用户信息已更改，请重新登录", 52002);
            //拦截
            return false;
        }

        if (user.getStopFlag().equals(1)) {
            redisTemplate.delete(token);
            writeJosn(response, "该账户已停用，请与管理员联系", 52003);
            //拦截
            return false;
        }

        //token（id）不为0，如果redis的velue为空，则账号密码过期
        Object loginUser = redisTemplate.opsForValue().get(token);
        if (StringUtils.isEmpty(loginUser)) {
            //过期
            writeJosn(response, "登录已过期", 52001);
            return false;
        }
        //第一次或者第n次请求 刷新保存时间
        redisTemplate.opsForValue().set(token, loginUser, 60 * 60 * 6, TimeUnit.SECONDS);
        //false 拦截
        return true;
    }

    /**
     * 响应给前台的json对象
     *
     * @param response
     * @param msg
     */
    private void writeJosn(HttpServletResponse response, String msg, Integer code) {
        PrintWriter writer = null;
        //如果token为空则证明没有登录
        try {
            //没有登录 就告诉前台 应该跳到登录页面 （前台用后置拦截器接受：在每次请求后响应之前拦截，就是有res以后执行成功函数之前）
            //告诉前台我要传的数据格式 和字符集
            response.setContentType("text/json;charset=utf-8");
            //要一个流
            writer = response.getWriter();
            //要把什么以json格式传给前台
            writer.write("{\"success\":false,\"result\":\" " + msg + "\",\"code\":\"" + code + "\" }");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }
}
