package com.honsoft.web.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
		"com.honsoft.web.mapper.h2" }, sqlSessionFactoryRef = "h2SqlSessionFactory", nameGenerator = UniqueNameGenerator.class)
@EnableJpaRepositories(basePackages = "com.honsoft.web.repository.h2", entityManagerFactoryRef = "h2EntityManagerFactory", transactionManagerRef = "h2TransactionManager")
public class DataSourceConfigH2 {

	@Autowired
	private Environment env;

	@Bean
	@ConfigurationProperties("h2.datasource.hikari") //DataSourceProperties 의 각 변수에 값을 셋팅함. url 로 지정한 값은 initializeDataSourceBuilder 에서 hikari에 맞게 jdbc-url로 변경됨
	public DataSourceProperties h2DataSourceProperties() {
		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		return dataSourceProperties;
	}

	@Bean(name = "h2DataSource", destroyMethod = "close")
	@Primary
	public DataSource h2DataSource() {
		return h2DataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
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

	@Bean(name = "h2TransactionManager")
	public PlatformTransactionManager h2TransactionManager() {
		EntityManagerFactory factory = h2EntityManagerFactory().getObject();
		return new JpaTransactionManager(factory);
	}

	// jpa
	@PersistenceContext(unitName = "h2Unit")
	@Bean(name = "h2EntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean h2EntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(h2DataSource());
		factory.setPackagesToScan(new String[] { "com.honsoft.web.entity" });
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		factory.setJpaProperties(jpaProperties);

		return factory;
	}

	@Bean
	public OpenEntityManagerInViewFilter h2OpenEntityManagerInViewFilter() {
		OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
		osivFilter.setEntityManagerFactoryBeanName("h2EntityManagerFactory");
		return osivFilter;
	}

	// mybatis
	@Bean(name = "h2SqlSessionFactory")
	public SqlSessionFactory h2SqlSessionFactory(@Qualifier("h2DataSource") DataSource h2DataSource,
			ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(h2DataSource);
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

	@Bean(name = "h2SqlSessiontemplate")
	public SqlSessionTemplate h2SqlSessionTemplate(SqlSessionFactory h2SqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(h2SqlSessionFactory);
	}

	@Bean
	public PlatformTransactionManager h2TxManager(@Qualifier("h2DataSource") DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}

}
