/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcService.java
* @Package com.lmstudio.rpc.server
* @author jason
* @Date 2017年6月23日 上午11:14:48
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.server;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Retention(RUNTIME)
@Target(TYPE)
@Component
/**
* TODO
* @ClassName: RpcService
* @author jason
*/
public @interface RpcService {

	Class<?> value();
}
