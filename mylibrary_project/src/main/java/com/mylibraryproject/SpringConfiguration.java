package com.mylibraryproject;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableJpaRepositories
public class SpringConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		jpaProperties.put("hibernate.show_sql", "true");
		//jpaProperties.put("database-platform", "org.hibernate.dialect.SQLServer2012Dialect");
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
		/*jpaProperties.put("hibernate.use-new-id-generator-mappings", "false");
		jpaProperties.put("hibernate.naming-strategy", "org.hibernate.cfg.ImprovedNamingStrategy");*/
		entityManagerFactory.setJpaProperties(jpaProperties);
		entityManagerFactory.setPackagesToScan("com.mylibraryproject");
		entityManagerFactory.setPersistenceProvider(new HibernatePersistenceProvider());
		return entityManagerFactory;
	}

	@Bean
	public DataSource dataSource() {
		HikariConfig config =new HikariConfig();
		config.setMaximumPoolSize(10);
		config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		config.setJdbcUrl("jdbc:sqlserver://localhost;databaseName=demo");
		config.setUsername("Demo");
		config.setPassword("demo-12345");

		/*config.setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
		config.addDataSourceProperty("serverName","");
		config.addDataSourceProperty("port","");
		config.addDataSourceProperty("databaseName","");
		config.addDataSourceProperty("users","Demo");
		config.addDataSourceProperty("password","demo-12345");
*/
		HikariDataSource dataSource = new HikariDataSource(config);

		/*dataSource.setJdbcUrl("jdbc:hsqldb:mem:test");
		dataSource.setUsername("sa");
		dataSource.setPassword("");*/
		return dataSource;
	}

	@Bean
	public JpaTransactionManager transactionManager(DataSource dataSource,
													EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}


}
