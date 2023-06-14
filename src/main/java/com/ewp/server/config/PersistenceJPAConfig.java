package com.ewp.server.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackages = {"com.ewp.server.persistence.entety"})
@EnableJpaRepositories(
    basePackages = "com.ewp.server.persistence.repository",
    entityManagerFactoryRef = "jpaEntityManager", 
    transactionManagerRef = "jpaTransactionManager"
)
public class PersistenceJPAConfig {

    private static final Logger logger = LogManager.getLogger(PersistenceJPAConfig.class);
    
    @Autowired
    private Environment env;

    /*@Primary
    @Bean(name = "jpaSessionFactory")
    public LocalSessionFactoryBean jpaSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(jpaDataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.ewp.server.persistence.entity" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }*/
    
    @Primary
    @Bean(name = "jpaEntityManager")
    public LocalContainerEntityManagerFactoryBean jpaEntityManager() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        //try {  // это для сервера gf 5.0
        entityManagerFactoryBean.setDataSource(jpaDataSource());
        entityManagerFactoryBean.setPackagesToScan(new String[] { "com.ewp.server.persistence.entity" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
        //} catch(Exception er) { logger.info(er.getMessage()); }
        return entityManagerFactoryBean;
    }
    
    final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate_sys.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate_sys.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getRequiredProperty("hibernate_sys.show_sql"));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", env.getProperty("hibernate_sys.cache.use_second_level_cache"));
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", env.getProperty("hibernate_sys.cache.use_query_cache"));
        //hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");
        return hibernateProperties;
    }

    @Primary
    @Bean(name = "jpaDataSource")
    public DataSource jpaDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc_sys.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc_sys.url"));
        dataSource.setUsername(env.getProperty("jdbc_sys.user"));
        dataSource.setPassword(env.getProperty("jdbc_sys.pass"));
        return dataSource;
    }

    @Primary
    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager jpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(jpaEntityManager().getObject());
        return transactionManager;
    }

}
