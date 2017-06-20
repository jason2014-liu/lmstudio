/**
* TODO
* @Project: lmstudio-common
* @Title: UserServiceImpl.java
* @Package com.lmstudio.example.service.impl
* @author jason
* @Date 2017年6月20日 上午10:25:13
* @Copyright
* @Version 
*/
package com.lmstudio.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmstudio.example.dao.UserMapper;
import com.lmstudio.example.domain.User;
import com.lmstudio.example.service.IUserService;

/**
 * TODO
 * 
 * @ClassName: UserServiceImpl
 * @author jason
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserById(String userId) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public PageInfo<User> queryUserByPage(User user, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		List<User> userList = userMapper.selectByPage(user);
		PageInfo<User> pageInfo = new PageInfo(userList);
		
		return pageInfo;
	}

}
