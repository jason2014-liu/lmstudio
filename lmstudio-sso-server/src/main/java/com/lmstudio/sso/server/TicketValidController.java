/**
* TODO
* @Project: lmstudio-sso-server
* @Title: TicketValidController.java
* @Package com.lmstudio.sso.server
* @author jason
* @Date 2017年7月11日 下午5:46:48
* @Copyright
* @Version 
*/
package com.lmstudio.sso.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* TODO
* @ClassName: TicketValidController
* @author jason
*/
@Controller
public class TicketValidController {

	private static Logger logger = LoggerFactory.getLogger(TicketValidController.class);
	
	public static Map<String,String> ticketMap = new HashMap<String, String>();
	
	@RequestMapping(value="/sso/ticketValidate")
	@ResponseBody
	public String validTicket(@RequestParam("ticket") String ticket){
		
		return ticketMap.get(ticket);
	}
}
