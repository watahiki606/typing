package com.typing.login.domain.service;

import java.util.List;

import com.typing.login.domain.model.User;

public interface RestService {

    public boolean insert(User user);

    public List<User> selectMany();

    public User selectOne(String userId);

    public boolean updateOne(User user);

    public boolean deleteOne(String userId);
}
