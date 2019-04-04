package com.typing.login.domain.repository.mybatis;

import java.util.List;

import com.typing.login.domain.model.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO m_user (" + "user_id" + ",password" + ",user_name" + ",role)" + "VALUES(" + "#{userId}"
            + ",#{password}" + ",#{userName}" + ",#{role})")

    public boolean insert(User user);

    @Select("SELECT user_id AS userId," + "password" + ",user_name AS userName" + ",role " + "FROM m_user "
            + "WHERE user_id = #{userId}")
    public User selectOne(String userId);

    @Select("SELECT user_id AS userId," + "password" + ",user_name AS userName" + ",role " + "FROM m_user")
    public List<User> selectMany();

    @Update("UPDATE m_user SET " + "password = #{password}," + "user_name = #{userName} "
            + " WHERE user_id = #{userId}")
    public boolean updateOne(User user);

    @Delete("DELETE FROM m_user WHERE user_id = #{userId}")
    public boolean deleteOne(String userId);
}
