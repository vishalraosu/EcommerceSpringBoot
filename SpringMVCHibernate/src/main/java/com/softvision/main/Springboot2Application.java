package com.softvision.main;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@SpringBootApplication
//for external file 

@PropertySource(value = "classpath:hibernateproperty.properties")
@ComponentScan("com.softvision")
public class Springboot2Application {

	@Value("${spring.datasource.driver-class-name}")
	private String className;
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String userName;
	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.jpa.properties.hibernate.dialect}")
	private String dialect;
	@Value("${spring.jpa.show-sql}")
	private String show_sql;
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String hbm2ddl;

	public static void main(String[] args) {
		SpringApplication.run(Springboot2Application.class, args);

	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(className);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;

	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.softvision");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", dialect);
		hibernateProperties.put("hibernate.show_sql", show_sql);
		hibernateProperties.put("hibernate.hbm2ddl.auto", hbm2ddl);
		sessionFactory.setHibernateProperties(hibernateProperties);
		return sessionFactory;
	}
}
