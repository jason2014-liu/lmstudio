/**
* TODO
* @Project: helloApp01
* @Title: IndexController.java
* @Package com.lmstudio.test1
* @author jason
* @Date 2017年6月12日 上午11:33:45
* @Copyright
* @Version 
*/
package com.lmstudio.test1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* TODO
* @ClassName: IndexController
* @author jason
*/
@RestController
@EnableAutoConfiguration
public class IndexController {

	@RequestMapping(value="/index")
	public String index(HttpServletRequest request, HttpServletResponse response){
		return "hello,App01";
	}
}
