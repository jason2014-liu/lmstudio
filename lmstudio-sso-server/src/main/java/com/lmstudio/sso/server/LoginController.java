/**
* TODO
* @Project: lmstudio-sso-server
* @Title: LoginController.java
* @Package com.lmstudio.sso.server
* @author jason
* @Date 2017年7月11日 下午5:46:16
* @Copyright
* @Version 
*/
package com.lmstudio.sso.server;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
* TODO
* @ClassName: LoginController
* @author jason
*/
@Controller
@RequestMapping(value="/sso")
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	 /**
	  * http://localhost:8888/lmstudio-sso-server/toLogin?service=
	 * TODO
	 * @Title: toLogin
	 * @param request
	 * @param response
	 * @return
	  */
	@RequestMapping(value="/toLogin")
	public String toLogin(HttpServletRequest request, HttpServletResponse response){
		
		String service = request.getParameter("service");
		if(service == null){
			logger.error("service is null，非法访问");
		}
		
		return "/login";
	}
	
	@RequestMapping(value="/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        String service = request.getParameter("service");
        
        if("testuser".equals(username)&& "testpwd".equals(password)){
        	//验证通过
        	//创建会话或写cookie，过滤器验证
        	//此处的参数，是相对于应用服务器存放应用的文件夹的根目录而言的(比如tomcat下面的webapp)，因此cookie.setPath("/");之后，可以在webapp文件夹下的所有应用共享cookie
        	Cookie cookie = new Cookie("sso",username);
        	cookie.setPath("/lmstudio-sso-server/");
        	cookie.setMaxAge(-1);//默认值是-1，表示关闭浏览器，cookie就会消失
        	response.addCookie(cookie);
        	
        	//重定向到service，带上ticket
        	String ticket = username+System.currentTimeMillis();
        	TicketValidController.ticketMap.put(ticket, username);
        	if(service != null){
        		StringBuilder url = new StringBuilder();
        		url.append(service);
        		if(service.indexOf("?")>=0){
        			url.append("&");
        		}else{
        			url.append("?");
        		}
        		url.append("ticket=").append(ticket);
        		
        		logger.info("登录验证通过，重定向"+url.toString());
        		response.sendRedirect(url.toString());
        	}
        	
        }else{
        	//response.sendRedirect("toLogin");
        	//返回错误信息
        }
		
		
	}
}
