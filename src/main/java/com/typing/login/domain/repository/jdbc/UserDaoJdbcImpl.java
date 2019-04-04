package com.typing.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.typing.login.domain.model.User;
import com.typing.login.domain.repository.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public int count() throws DataAccessException {

        int count = jdbc.queryForObject("select count(1) from m_user", Integer.class);
        return count;
    }

    @Override
    public int insertOne(User user) throws DataAccessException {

        // パスワード暗号化
        String password = passwordEncoder.encode(user.getPassword());
        int rowNumber = jdbc.update("insert into m_user (user_id,password,user_name,role)values(?,?,?,?)",
                user.getUserId(), password, user.getUserName(), user.getRole());
        return rowNumber;
    }

    @Override
    public User selectOne(String userId) throws DataAccessException {

        Map<String, Object> map = jdbc.queryForMap("select * from m_user where user_id = ?", userId);
        User user = new User();
        user.setUserId((String) map.get("user_id"));
        user.setPassword((String) map.get("password"));
        user.setUserName((String) map.get("user_name"));
        user.setRole((String) map.get("role"));
        return user;
    }

    @Override
    public List<User> selectMany() throws DataAccessException {

        List<Map<String, Object>> getList = jdbc.queryForList("select * from m_user");
        List<User> userList = new ArrayList<>();
        for (Map<String, Object> map : getList) {
            User user = new User();
            user.setUserId((String) map.get("user_id"));
            user.setPassword((String) map.get("password"));
            user.setUserName((String) map.get("user_name"));
            user.setRole((String) map.get("role"));
            userList.add(user);
        }
        return userList;
    }

    @Override
    public int updateOne(User user) throws DataAccessException {

        // パスワード暗号化
        String password = passwordEncoder.encode(user.getPassword());
        int rowNumber = jdbc.update("update m_user set password = ?, user_name = ? where user_id = ?", password,
                user.getUserName(), user.getUserId());
        return rowNumber;
    }

    @Override
    public int deleteOne(String userId) throws DataAccessException {

        int rowNumber = jdbc.update("delete from m_user where user_id = ?", userId);
        return rowNumber;
    }

}
