package com.javazdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javazdm.mapper.UserMapper;
import com.javazdm.service.IUserService;
import com.javazdm.utils.TokenUtil;
import com.javazdm.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.javazdm.entity.User;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TokenUtil tokenUtil;

    public RespBean getUserList(UserPageNationVo userPageNationVo){
        try {
            QueryWrapper wrapper = new QueryWrapper();
            if (!StringUtils.isEmpty(userPageNationVo.getStopFlag())) {
                wrapper.eq("stop_flag", userPageNationVo.getStopFlag());
            }
            System.out.println(userPageNationVo.getNickName());
            if (!StringUtils.isEmpty(userPageNationVo.getNickName())) {
                wrapper.like("nick_name", userPageNationVo.getNickName());
            }
            Page<User> userIPage = new Page<>(userPageNationVo.getCurrent(), userPageNationVo.getSize());
            IPage<User> userPage = userMapper.selectPage(userIPage, wrapper);
            for (User item : userPage.getRecords()) {
                item.deletePassword();
            }
            return RespBean.success(userPage);
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RespBean.error(RespBeanEnum.ERROR, e);
        }
    }

    public RespBean login(LoginVo loginVo) {
        try {
            String username = loginVo.getUsername();
            String password = loginVo.getPassword();
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                return RespBean.error(RespBeanEnum.ACCPWD_NOT_EMPTY);
            }
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("username", username);
            User user = userMapper.selectOne(wrapper);
            if (StringUtils.isEmpty(user)) {
                return RespBean.error(RespBeanEnum.ACC_ERROR);
            }
            if (!user.getPassword().equals(password)) {
                return RespBean.error(RespBeanEnum.PWD_ERROR);
            }
            String token = tokenUtil.createToken(user);
            redisTemplate.opsForValue().set(token, user.getId(), 60 * 60 * 6, TimeUnit.SECONDS);
            UserInfoVo userInfoVo = userInfo(user);
            userInfoVo.setToken(token);
            return RespBean.success(userInfoVo);
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RespBean.error(RespBeanEnum.ERROR, e);
        }
    }

    private UserInfoVo userInfo(User user) {
        UserInfoVo userInfo = new UserInfoVo();
        userInfo.setId(user.getId());
        userInfo.setUserName(user.getUsername());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setCreateAt(user.getCreateAt());
        userInfo.setUpdateAt(user.getUpdateAt());
        userInfo.setNickName(user.getNickName());
        userInfo.setSex(user.getSex());
        return userInfo;
    }
}
