package com.honsoft.web.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

@Configuration
@Order(0)
@PropertySource(value = { "classpath:jdbc.properties" }, ignoreResourceNotFound = true)
//@ImportResource(value="classpath:hsql_cfg.xml")
public class HsqldbServerConfig {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(HsqldbServerConfig.class);

	@Autowired
	private Environment env;

//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public HsqldbServer hsqldbServer() {
//    	Properties props = new Properties();
//    	props.setProperty("server.port", env.getProperty("hsqldb.server.port"));
//    	props.setProperty("server.database.0", env.getProperty("hsqldb.server.database.0"));
//    	props.setProperty("server.dbname.0", env.getProperty("hsqldb.server.dbname.0"));
//    	
//    	return new HsqldbServer(props);
//    }

}
