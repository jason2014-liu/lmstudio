/**
* TODO
* @Project: lmstudio-common
* @Title: UserServiceTest.java
* @Package com.lmstudio.example.service
* @author jason
* @Date 2017年6月20日 上午10:37:51
* @Copyright
* @Version 
*/
package com.lmstudio.example.service;

import static org.junit.Assert.assertArrayEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.lmstudio.example.domain.User;

/**
 * TODO
 * 
 * @ClassName: UserServiceTest
 * @author jason
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-mybatis.xml" })
public class UserServiceTest {

	@Autowired
	private IUserService userService;

	// @Test
	public void testGetUserById() {
		User user = userService.getUserById("001");

		User actualUser = new User();
		actualUser.setUserId("001");
		actualUser.setUserName("幺狗");

		assertArrayEquals(user.getUserId().getBytes(), actualUser.getUserId().getBytes());
		assertArrayEquals(user.getUserName().getBytes(), actualUser.getUserName().getBytes());

		System.out.println(user.getUserId() + user.getUserName() + user.getUserSalary() + user.getUserBirthday());
	}

	@Test
	public void testQueryUserByPage(){
		User param = new User();
		//param.setUserId("001");
		param.setUserName("幺狗");
		
		PageInfo<User> page = userService.queryUserByPage(param, 1, 2);
		
		System.out.println(page.toString());
		List<User> userList = page.getList();
		for(User user:userList){
			System.out.println(user.getUserId() + user.getUserName() + user.getUserSalary() + user.getUserBirthday());
			
		}
	}
}
