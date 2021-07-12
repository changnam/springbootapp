package com.honsoft.web.listener;

import java.util.Map;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;

@Component
public class ServletContextAwareBean implements ServletContextAware{
	private static Logger logger = LoggerFactory.getLogger(ServletContextAwareBean.class);
	
	@Override
	public void setServletContext(ServletContext servletContext) {
	   	//create database connection from init parameters and set it to context
    	//DBConnectionManager dbManager = new DBConnectionManager(url, u, p);
    	//ctx.setAttribute("DBManager", dbManager);
		logger.info("Database connection initialized for Application.");
    	
    	Map<String, ? extends ServletRegistration> servletRegistrations = servletContext.getServletRegistrations();
		

    	logger.info("------------------ registerd servlets -------------------------");
    	for (String servletName : servletRegistrations.keySet()) {
    		//System.out.println("==> "+);
    		logger.info("name: " + servletName + " , class: " +  servletRegistrations.get(servletName).getClassName() + " , mapping: " + servletRegistrations.get(servletName).getMappings() );
    	}
    	logger.info("--------------------------------------------------------------");
    	
    	Map<String, ? extends FilterRegistration> filterRegistrations = servletContext.getFilterRegistrations();
    	
    	logger.info("------------------ registerd filters -------------------------");
    	for (String filterName : filterRegistrations.keySet()) {
    		//System.out.println("==> "+);
    		logger.info("name: " + filterName + " , class: " +  filterRegistrations.get(filterName).getClassName() + " , mapping: " + filterRegistrations.get(filterName).getUrlPatternMappings() + filterRegistrations.get(filterName).getServletNameMappings());
    	}
    	
    	//ctx.getServlet("dispatcher").getServletConfig().g
    	logger.info("--------------------------------------------------------------");
  
		
	}

}
