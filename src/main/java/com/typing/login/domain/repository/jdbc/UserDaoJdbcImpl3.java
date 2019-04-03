package com.typing.login.domain.repository.jdbc;

import java.util.List;
import com.typing.login.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("UserDaoJdbcImpl3")
public class UserDaoJdbcImpl3 extends UserDaoJdbcImpl {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public User selectOne(String userId) {

        String sql = "select * from m_user where user_id = ?";

        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);

        return jdbc.queryForObject(sql, rowMapper, userId);
    }

    @Override
    public List<User> selectMany() {

        String sql = "select * from m_user";

        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);

        return jdbc.query(sql, rowMapper);
    }

}