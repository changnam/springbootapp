package com.honsoft.web.config;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.hsqldb.server.Server;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource(value = { "classpath:jdbc.properties" }, ignoreResourceNotFound = true)
@MapperScan(value = {
		"com.honsoft.web.mapper.hsqldb" }, sqlSessionFactoryRef = "hsqldbSqlSessionFactory", nameGenerator = UniqueNameGenerator.class)
@EnableJpaRepositories(basePackages = "com.honsoft.web.repository.hsqldb", entityManagerFactoryRef = "hsqldbEntityManagerFactory", transactionManagerRef = "hsqldbTransactionManager")
public class DataSourceConfigHsqldb {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(DataSourceConfigHsqldb.class);

	@Autowired
	private Environment env;

	@Bean
	@ConfigurationProperties("hsqldb.datasource.hikari")
	public DataSourceProperties hsqldbDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "hsqldbDataSource", destroyMethod = "close")
	public DataSource hsqldbDataSource() {
		return hsqldbDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
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
	public PlatformTransactionManager hsqldbTransactionManager() {
		EntityManagerFactory factory = hsqldbEntityManagerFactory().getObject();
		return new JpaTransactionManager(factory);
	}

	// jpa
	@PersistenceContext(unitName = "hsqldbUnit")
	@Bean(name = "hsqldbEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean hsqldbEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(hsqldbDataSource());
		factory.setPackagesToScan(new String[] { "com.honsoft.web.entity" });
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		jpaProperties.put("hibernate.dialect", env.getProperty("hsqldb.datasource.dialect"));
		factory.setJpaProperties(jpaProperties);

		return factory;
	}

	@Bean
	public OpenEntityManagerInViewFilter hsqldbOpenEntityManagerInViewFilter() {
		OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
		osivFilter.setEntityManagerFactoryBeanName("hsqldbEntityManagerFactory");
		return osivFilter;
	}

	// mybatis
	@Bean(name = "hsqldbSqlSessionFactory")
	public SqlSessionFactory hsqldbSqlSessionFactory(@Qualifier("hsqldbDataSource") DataSource hsqldbDataSource,
			ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(hsqldbDataSource);
		sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
		// sqlSessionFactoryBean.setTypeAliasesPackage("com.honsoft.web.dto");
		// sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		// sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:sql/**/*.xml"));

		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		sqlSessionFactoryBean.setConfiguration(configuration);

		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "hsqldbSqlSessiontemplate")
	public SqlSessionTemplate hsqldbSqlSessionTemplate(SqlSessionFactory hsqldbSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(hsqldbSqlSessionFactory);
	}

	@Bean
	public PlatformTransactionManager hsqldbTxManager(@Qualifier("hsqldbDataSource") DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}

}
