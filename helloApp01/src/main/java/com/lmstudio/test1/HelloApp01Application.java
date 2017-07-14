package com.lmstudio.test1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.lmstudio.sso.client.SsoClientFilter;


@SpringBootApplication
public class HelloApp01Application {

	public static void main(String[] args) {
		SpringApplication.run(HelloApp01Application.class, args);
	}
	
	@Bean
	public FilterRegistrationBean ssoFilterRegistration(){
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new SsoClientFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.addInitParameter("sso_login_url", "http://localhost:8888/lmstudio-sso-server/sso/toLogin");
		registrationBean.addInitParameter("sso_ticketValidate_url", "http://localhost:8888/lmstudio-sso-server/sso/ticketValidate");
		registrationBean.setName("ssoFilter");
		return registrationBean;
	}
}
