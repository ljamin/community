package com.ljm.community.mapper;

import com.ljm.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,bio,avatar_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
    @Select("select * from user where id=#{id} ")
    User findById(@Param("id") Integer id);
}
