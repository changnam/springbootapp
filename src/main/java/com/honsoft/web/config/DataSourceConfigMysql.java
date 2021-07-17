package com.honsoft.web.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource(value={"classpath:jdbc.properties"},ignoreResourceNotFound = true)
@EnableJpaRepositories(basePackages = "com.honsoft.web.repository.mysql", entityManagerFactoryRef = "mysqlEntityManagerFactory", transactionManagerRef = "mysqlTransactionManager")
public class DataSourceConfigMysql {
	
	@Autowired
	private Environment env;
	
	//--------------------------------------------------------------------------------------------
	// datasource
	@Bean(name="mysqlDataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix="mysql.datasource.hikari")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean
	public DataSourceInitializer mysqlDataSourceInitializer(@Qualifier("mysqlDataSource") DataSource datasource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/mysql/schema-mysql.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/mysql/data-mysql.sql"));
		resourceDatabasePopulator.setIgnoreFailedDrops(true);
		
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(datasource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		dataSourceInitializer.setEnabled(env.getProperty("mysql.datasource.initialize", Boolean.class, false));
        
		return dataSourceInitializer;
	}
	
	
	@Bean(name = "mysqlTransactionManager")
	@Primary
    public PlatformTransactionManager mysqlTransactionManager()
    {
        EntityManagerFactory factory = mysqlEntityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

	// jpa
	@PersistenceContext(unitName = "mysqlUnit")
	@Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(mysqlDataSource());
        factory.setPackagesToScan(new String[]{"com.honsoft.web.entity"});
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
     
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
        factory.setJpaProperties(jpaProperties);
     
        return factory;
    }
	
	 
    @Bean
    public OpenEntityManagerInViewFilter mysqlOpenEntityManagerInViewFilter()
    {
        OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
        osivFilter.setEntityManagerFactoryBeanName("mysqlEntityManagerFactory");
        return osivFilter;
    }
	
}
