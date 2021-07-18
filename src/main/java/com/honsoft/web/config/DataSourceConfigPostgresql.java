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
		"com.honsoft.web.mapper.postgresql" }, sqlSessionFactoryRef = "postgresqlSqlSessionFactory", nameGenerator = UniqueNameGenerator.class)
@EnableJpaRepositories(basePackages = "com.honsoft.web.repository.postgresql", entityManagerFactoryRef = "postgresqlEntityManagerFactory", transactionManagerRef = "postgresqlTransactionManager")
public class DataSourceConfigPostgresql {

	@Autowired
	private Environment env;

	// datasource
	@Bean(name = "postgresqlDataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix = "postgresql.datasource.hikari")
	public DataSource postgresqlDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean
	public DataSourceInitializer postgresqlDataSourceInitializer(
			@Qualifier("postgresqlDataSource") DataSource datasource) {
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

	@Bean(name = "postgresqlTransactionManager")
	public PlatformTransactionManager postgresqlTransactionManager() {
		EntityManagerFactory factory = postgresqlEntityManagerFactory().getObject();
		return new JpaTransactionManager(factory);
	}

	// jpa
	@PersistenceContext(unitName = "postgresqlUnit")
	@Bean(name = "postgresqlEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean postgresqlEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(postgresqlDataSource());
		factory.setPackagesToScan(new String[] { "com.honsoft.web.entity" });
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		factory.setJpaProperties(jpaProperties);

		return factory;
	}

	@Bean
	public OpenEntityManagerInViewFilter postgresqlOpenEntityManagerInViewFilter() {
		OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
		osivFilter.setEntityManagerFactoryBeanName("postgresqlEntityManagerFactory");
		return osivFilter;
	}

	// mybatis
	@Bean(name = "postgresqlSqlSessionFactory")
	public SqlSessionFactory postgresqlSqlSessionFactory(
			@Qualifier("postgresqlDataSource") DataSource postgresqlDataSource, ApplicationContext applicationContext)
			throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(postgresqlDataSource);
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

	@Bean(name = "postgresqlSqlSessiontemplate")
	public SqlSessionTemplate postgresqlSqlSessionTemplate(SqlSessionFactory postgresqlSqlSessionFactory)
			throws Exception {
		return new SqlSessionTemplate(postgresqlSqlSessionFactory);
	}

	@Bean
	public PlatformTransactionManager postgresqlTxManager(@Qualifier("postgresqlDataSource") DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}

}
