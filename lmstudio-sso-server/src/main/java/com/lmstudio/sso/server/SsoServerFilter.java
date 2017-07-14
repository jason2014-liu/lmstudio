/**
* TODO
* @Project: lmstudio-sso-server
* @Title: SsoServerFilter.java
* @Package com.lmstudio.sso.server
* @author jason
* @Date 2017年7月11日 下午5:09:04
* @Copyright
* @Version 
*/
package com.lmstudio.sso.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * 
 * @ClassName: SsoServerFilter
 * @author jason
 */
public class SsoServerFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(SsoServerFilter.class);


	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		HttpSession session = request.getSession();
		logger.debug("sessionId={},createTime={}",session.getId(),session.getCreationTime());
		printCookies(request);

		String service = request.getParameter("service");
		String ticket = request.getParameter("ticket");

		Cookie[] cookies = request.getCookies();
		String username = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("sso".equals(cookie.getName())) {
					username = cookie.getValue();
					break;
				}
			}
		}
		
		if(service == null && ticket !=null){
			//ticket 验证
			filterChain.doFilter(request, response);
			return;
		}
		
		if(!"".equals(username)){
			//已登录过
			//重定向到service，带上ticket
        	String newTicket = username+System.currentTimeMillis();
        	TicketValidController.ticketMap.put(newTicket, username);
        	if(service != null){
        		StringBuilder url = new StringBuilder();
        		url.append(service);
        		if(service.indexOf("?")>=0){
        			url.append("&");
        		}else{
        			url.append("?");
        		}
        		url.append("ticket=").append(newTicket);
        		
        		logger.info("已登录过，直接重定向"+url.toString());
        		response.sendRedirect(url.toString());
        	}
		}else{
			filterChain.doFilter(request, response);
		}

		
	}
	
	private void printCookies(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if(cookies == null){
			logger.debug("cookie is null");
			return;
		}
		logger.debug("-----------cookie values----------");
		for(Cookie cookie : cookies){
			logger.debug(cookie.getName()+":"+cookie.getValue()+";domain="+cookie.getDomain()+";path="+cookie.getPath());
		}
		logger.debug("-----------cookie values----------");
	}


	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
