package com.typing.login.domain.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.typing.login.domain.model.User;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();

        user.setUserId(rs.getString("user_id"));
        user.setPassword(rs.getString("password"));
        user.setUserName(rs.getString("user_name"));
        user.setRole(rs.getString("role"));

        return user;
    }

}