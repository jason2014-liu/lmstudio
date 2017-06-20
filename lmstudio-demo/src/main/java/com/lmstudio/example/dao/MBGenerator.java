/**
* TODO
* @Project: interframe-web-admin
* @Title: MBGenerator.java
* @Package com.interframe.hr.repository.mybatis
* @author jason
* @Date 2016年9月18日 上午10:28:16
* @Copyright
* @Version 
*/
package com.lmstudio.example.dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
* TODO
* @ClassName: MBGenerator
* @author jason
*/
public class MBGenerator {

	/**
	* TODO
	* @Title: main
	* @param args
	 * @throws XMLParserException 
	 * @throws IOException 
	 * @throws InvalidConfigurationException 
	 * @throws InterruptedException 
	 * @throws SQLException 
	*/
	public static void main(String[] args) throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
		// TODO Auto-generated method stub

		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,callback,warnings);
		myBatisGenerator.generate(null);
		
	}

}
