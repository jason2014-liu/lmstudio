/**
* TODO
* @Project: lmstudio-sso-client
* @Title: SsoClientFilter.java
* @Package com.lmstudio.sso.client
* @author jason
* @Date 2017年7月11日 下午4:10:26
* @Copyright
* @Version 
*/
package com.lmstudio.sso.client;

import java.io.IOException;
import java.net.URLEncoder;

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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* TODO
* @ClassName: SsoClientFilter
* @author jason
*/
public class SsoClientFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(SsoClientFilter.class);
	
	String sso_login_url = null;
	String sso_ticketValidate_url = null;
	
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		
		//Returns the current session associated with this request, or if the request does not have a session, creates one
		HttpSession session = request.getSession();
		logger.debug("sessionId={},createTime={}",session.getId(),session.getCreationTime());
		printCookies(request);
		
		String username = session.getAttribute("username") == null ? null:(String)session.getAttribute("username");
		String ticket = request.getParameter("ticket");
		
		if(username == null){
			//尚未登录建立会话
			
			if(ticket != null){
				//sso server 重定向过来的请求，即 已在sso server完成验证，且生成了客户端请求的应用系统的令牌；
				
				//应用系统到sso server验证令牌，如果通过，返回username
				username = verifyTicket(ticket);
				if(username !=null){
					//验证通过，创建会话
					session.setAttribute("username", username);
					filterChain.doFilter(request, response);
				}else{
					//验证不通过，抛出异常
					throw new RuntimeException("sso server 验证异常，请联系系统管理员");
				}
			}else{
				//初次请求，重定向到登录界面
				String serviceUrl = URLEncoder.encode(request.getRequestURL().toString(),"UTF-8");
				response.sendRedirect(sso_login_url+"?service="+serviceUrl);
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


	/**
	* TODO 授权令牌的验证 ，如果验证不同过，返回null
	* @Title: verifyTicket
	* @param ticket
	* @return username (用于创建会话)
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private String verifyTicket(String ticket) throws ClientProtocolException, IOException{
		//用httpClient请求sso server验证,sso server 端： 一个username+多个ticket（一个ticket对应一个应用系统）
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = null;
		try {
			HttpGet httpget = new HttpGet(sso_ticketValidate_url+"?ticket="+ticket);

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            responseBody = httpclient.execute(httpget, responseHandler);
        } finally {
            httpclient.close();
        }
		return responseBody;
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

		sso_login_url = filterConfig.getInitParameter("sso_login_url");
		sso_ticketValidate_url = filterConfig.getInitParameter("sso_ticketValidate_url");
		if(sso_login_url == null){
			throw new IllegalArgumentException("sso_login_url 未配置");
		}
		if(sso_ticketValidate_url == null){
			throw new IllegalArgumentException("sso_ticketValidate_url 未配置");
		}
	}
	
	
	

}
