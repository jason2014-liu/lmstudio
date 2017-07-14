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
package com.lmstudio.test2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* TODO
* @ClassName: IndexController
* @author jason
*/
@RestController
public class IndexController {

	@RequestMapping(value="/index")
	public String index(){
		return "hello,App02";
	}
}
