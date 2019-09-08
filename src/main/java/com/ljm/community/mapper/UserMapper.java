package com.ljm.community.mapper;

import com.ljm.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,bio) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio})")
    void insert(User user);
}
