package com.typing.login.domain.service.jdbc;

import java.util.List;

import com.typing.login.domain.model.User;
import com.typing.login.domain.repository.UserDao;
import com.typing.login.domain.service.RestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RestServiceJdbcImpl implements RestService {

    @Autowired
    @Qualifier("UserDaoJdbcImpl")
    UserDao dao;

    @Override
    public boolean insert(User user) {
        int result = dao.insertOne(user);
        if (result == 0) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public List<User> selectMany() {
        return dao.selectMany();
    }

    @Override
    public User selectOne(String userId) {
        return dao.selectOne(userId);
    }

    @Override
    public boolean updateOne(User user) {
        int result = dao.updateOne(user);
        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean deleteOne(String userId) {
        int result = dao.deleteOne(userId);
        if (result == 0) {
            return false;
        } else {
            return true;
        }

    }

}