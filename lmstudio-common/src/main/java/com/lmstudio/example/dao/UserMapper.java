package com.lmstudio.example.dao;

import java.util.List;

import com.lmstudio.example.domain.User;

public interface UserMapper {
	int deleteByPrimaryKey(String userId);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(String userId);

	List<User> selectByPage(User user);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
}