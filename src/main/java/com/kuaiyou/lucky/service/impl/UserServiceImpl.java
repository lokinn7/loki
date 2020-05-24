package com.kuaiyou.lucky.service.impl;

import com.kuaiyou.lucky.entity.User;
import com.kuaiyou.lucky.mapper.UserMapper;
import com.kuaiyou.lucky.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yardney
 * @since 2020-05-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
