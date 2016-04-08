package com.magm.web.spring.config;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@EnableTransactionManagement
@PropertySource({ "classpath:persistence.properties" })
public class PersistenceConfig {
	private static Logger LOG = LoggerFactory.getLogger(PersistenceConfig.class);
	@Autowired
	private Environment env;

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packages.scan").split(","));
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory) {
		final HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	final Properties hibernateProperties() {
		final Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		hibernateProperties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		hibernateProperties.setProperty("hibernate.enable_lazy_load_no_trans",
				env.getProperty("hibernate.enable_lazy_load_no_trans"));

		// hibernateProperties.setProperty("hibernate.current_session_context_class",
		// env.getProperty("hibernate.current_session_context_class"));
		hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");

		return hibernateProperties;
	}

	@Bean
	public DataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		try {
			dataSource.setDriverClass(env.getProperty("jdbc.driverClassName"));
		} catch (PropertyVetoException e) {
			throw new IllegalArgumentException("Wrong driver class");
		}
		dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		dataSource.setMinPoolSize(Integer.parseInt(env.getProperty("hibernate.c3p0.min_size")));
		dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("hibernate.c3p0.max_size")));
		dataSource.setAcquireIncrement(Integer.parseInt(env.getProperty("hibernate.c3p0.acquire_increment")));
		dataSource.setIdleConnectionTestPeriod(Integer.parseInt(env.getProperty("hibernate.c3p0.maxIdleTime")));
		dataSource.setMaxStatements(Integer.parseInt(env.getProperty("hibernate.c3p0.max_statements")));
		dataSource.setPreferredTestQuery(env.getProperty("hibernate.c3p0.preferredTestQuery"));
		dataSource.setTestConnectionOnCheckout(
				Boolean.parseBoolean(env.getProperty("hibernate.c3p0.testConnectionOnCheckout")));
		try {
			dataSource.setLoginTimeout(Integer.parseInt(env.getProperty("hibernate.c3p0.timeout")));
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return dataSource;
	}
	/*
	 * @Bean public DataSource basicDataSource() { final BasicDataSource
	 * dataSource = new BasicDataSource();
	 * dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	 * dataSource.setUrl(env.getProperty("jdbc.url"));
	 * dataSource.setUsername(env.getProperty("jdbc.user"));
	 * dataSource.setPassword(env.getProperty("jdbc.pass"));
	 * 
	 * return dataSource; }
	 */
}
