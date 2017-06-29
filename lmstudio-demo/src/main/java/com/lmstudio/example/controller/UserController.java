/**
* TODO
* @Project: lmstudio-demo
* @Title: UserController.java
* @Package com.lmstudio.example.controller
* @author jason
* @Date 2017年6月21日 上午10:37:46
* @Copyright
* @Version 
*/
package com.lmstudio.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lmstudio.example.domain.User;
import com.lmstudio.example.service.IUserService;

/**
* TODO
* @ClassName: UserController
* @author jason
*/
@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/queryUser",method = RequestMethod.GET)
	@ResponseBody
	public PageInfo<User> queryUserByPage(){
		User param = new User();
		//param.setUserId("001");
		param.setUserName("幺狗");
		return userService.queryUserByPage(param, 1, 2);
	}
}
