package com.typing.login.domain.service.mybatis;

import java.util.List;

import com.typing.login.domain.model.User;

import com.typing.login.domain.repository.mybatis.UserMapper2;
import com.typing.login.domain.service.RestService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("RestServiceMybatisImpl")
public class RestServiceMybatisImpl implements RestService {

    @Autowired
    UserMapper2 userMapper;

    @Override
    public boolean insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public List<User> selectMany() {
        return userMapper.selectMany();
    }

    @Override
    public User selectOne(String userId) {
        return userMapper.selectOne(userId);
    }

    @Override
    public boolean updateOne(User user) {
        return userMapper.updateOne(user);
    }

    @Override
    public boolean deleteOne(String userId) {
        return userMapper.deleteOne(userId);
    }

}
