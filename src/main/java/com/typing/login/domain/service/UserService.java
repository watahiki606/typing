package com.typing.login.domain.service;

import java.util.List;

import com.typing.login.domain.model.User;
import com.typing.login.domain.repository.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

    @Autowired
    @Qualifier("UserDaoJdbcImpl")
    UserDao dao;

    public boolean insert(User user) {

        int rowNumber = dao.insertOne(user);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;

    }

    public int count() {
        return dao.count();
    }

    public List<User> selectMany() {
        return dao.selectMany();
    }

    public User selectOne(String userId) {
        return dao.selectOne(userId);
    }

    public boolean updateOne(User user) {

        int rowNumber = dao.updateOne(user);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;

    }

    public boolean deleteOne(String userId) {

        int rowNumber = dao.deleteOne(userId);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }
}