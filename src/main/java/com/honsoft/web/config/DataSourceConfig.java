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
@EnableJpaRepositories(basePackages = "com.honsoft.web.repository", entityManagerFactoryRef = "hsqldbEntityManagerFactory", transactionManagerRef = "hsqldbTransactionManager")
public class DataSourceConfig {
	
	@Autowired
	private Environment env;
	
	// datasource
	@Bean(name = "h2DataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix = "h2.datasource.hikari")
	@Primary
	public DataSource h2DataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean
	public DataSourceInitializer h2DataSourceInitializer(@Qualifier("h2DataSource") DataSource datasource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/h2/schema-h2.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/h2/data-h2.sql"));
		resourceDatabasePopulator.setIgnoreFailedDrops(true);
		
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(datasource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		dataSourceInitializer.setEnabled(env.getProperty("h2.datasource.initialize", Boolean.class, false));
        
		return dataSourceInitializer;
	}
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
	//--------------------------------------------------------------------------------------------
	
	// datasource
	@Bean(name="hsqldbDataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix="hsqldb.datasource.hikari")
	public DataSource hsqldbDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean
	public DataSourceInitializer hsqldbDataSourceInitializer(@Qualifier("hsqldbDataSource") DataSource datasource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/hsqldb/schema-hsqldb.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/hsqldb/data-hsqldb.sql"));
		resourceDatabasePopulator.setIgnoreFailedDrops(true);
		
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(datasource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		dataSourceInitializer.setEnabled(env.getProperty("hsqldb.datasource.initialize", Boolean.class, false));
        
		return dataSourceInitializer;
	}
	
	@Bean(name = "hsqldbTransactionManager")
	@Primary
    public PlatformTransactionManager hsqldbTransactionManager()
    {
        EntityManagerFactory factory = hsqldbEntityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

	// jpa
	@PersistenceContext(unitName = "hsqldbUnit")
	@Bean(name = "hsqldbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean hsqldbEntityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(hsqldbDataSource());
        factory.setPackagesToScan(new String[]{"com.honsoft.web.entity"});
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
     
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
        factory.setJpaProperties(jpaProperties);
     
        return factory;
    }
	
	 
    @Bean
    public OpenEntityManagerInViewFilter hsqldbOpenEntityManagerInViewFilter()
    {
        OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
        osivFilter.setEntityManagerFactoryBeanName("hsqldbEntityManagerFactory");
        return osivFilter;
    }
    
	//--------------------------------------------------------------------------------------------
	
	// datasource
	@Bean(name="postgresqlDataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix="postgresql.datasource.hikari")
	public DataSource postgresqlDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean
	public DataSourceInitializer postgresqlDataSourceInitializer(@Qualifier("postgresqlDataSource") DataSource datasource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/postgresql/schema-postgresql.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/postgresql/data-postgresql.sql"));
		resourceDatabasePopulator.setIgnoreFailedDrops(true);
		
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(datasource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		dataSourceInitializer.setEnabled(env.getProperty("postgresql.datasource.initialize", Boolean.class, false));
        
		return dataSourceInitializer;
	}
	
	//--------------------------------------------------------------------------------------------
	
	// datasource
	@Bean(name="oracleDataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix="oracle.datasource.hikari")
	public DataSource oracleDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean
	public DataSourceInitializer oracleDataSourceInitializer(@Qualifier("oracleDataSource") DataSource datasource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/oracle/schema-oracle.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("ddl/oracle/data-oracle.sql"));
		resourceDatabasePopulator.setIgnoreFailedDrops(true);
		
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(datasource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		dataSourceInitializer.setEnabled(env.getProperty("oracle.datasource.initialize", Boolean.class, false));
        
		return dataSourceInitializer;
	}
	
}
