/**
* TODO
* @Project: lmstudio-common
* @Title: IUserService.java
* @Package com.lmstudio.example.service
* @author jason
* @Date 2017年6月20日 上午10:23:11
* @Copyright
* @Version 
*/
package com.lmstudio.example.service;

import com.github.pagehelper.PageInfo;
import com.lmstudio.example.domain.User;

/**
 * TODO
 * 
 * @ClassName: IUserService
 * @author jason
 */
public interface IUserService {

	User getUserById(String userId);

	PageInfo<User> queryUserByPage(User user, Integer pageNum, Integer pageSize);
}
