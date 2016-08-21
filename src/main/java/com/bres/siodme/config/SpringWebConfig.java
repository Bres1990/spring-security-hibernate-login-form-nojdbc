package com.bres.siodme.config;

/**
 * Created by Adam on 2016-07-06.
 */

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

@EnableWebMvc
@EnableSpringDataWebSupport
@EnableJpaRepositories("com.bres.siodme.web.repository")
@EnableTransactionManagement
//@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan("com.bres.siodme")
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("validation");
        return messageSource;
    }

//    @Bean
//    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
//        Resource sourceData = new ClassPathResource("users.json");
//
//        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
//        factory.setResources(new Resource[] { sourceData });
//
//        return factory;
//    }

//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.bres.siodme");
//
//        return factory.getObject();
//    }

//    @Bean
//    public PlatformTransactionManager transactionManager() {
//
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory());
//        return txManager;
//    }

//    @Bean
//    public DriverManagerDataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//        dataSource.setUrl(env.getProperty("jdbc.url"));
//        dataSource.setUsername(env.getProperty("jdbc.username"));
//        dataSource.setPassword(env.getProperty("jdbc.password"));
//
//        return dataSource;
//    }

    //    @Bean
    //    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
    //
    //        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    //        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
    //        entityManagerFactory.setPackagesToScan("com.bres.siodme");
    //        entityManagerFactory.setJpaDialect(jpaDialect());
    //        //entityManagerFactory.afterPropertiesSet();
    //
    //        return entityManagerFactory;
    //    }
    //
    //    @Bean
    //    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    //        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    //        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
    //
    //        return jpaTransactionManager;
    //    }
    //
    //    @Bean
    //    JpaVendorAdapter jpaVendorAdapter() {
    //        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    //        jpaVendorAdapter.setGenerateDdl(true);
    //        jpaVendorAdapter.setDatabasePlatform("org.springframework.orm.jpa.vendor.HibernateJpaDialect;");
    //        //jpaVendorAdapter.setShowSql(true);
    //
    //        return jpaVendorAdapter;
    //    }
    //
    //    @Bean
    //    HibernateJpaDialect jpaDialect() {
    //        HibernateJpaDialect jpaDialect = new HibernateJpaDialect();
    //
    //        return jpaDialect;
    //
    //
    //    }

    @Bean
    public DataSource dataSource() {
        final EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
        return embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.bres.siodme.web.model");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(Boolean.TRUE);
        vendorAdapter.setDatabase(Database.H2);
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setPersistenceUnitName("persistenceUnit");
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public InitializingBean dataPopulator(final DataSource dataSource) {
        return () -> {
            final DatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("test-data.sql"));
            DatabasePopulatorUtils.execute(populator, dataSource);
        };
    }
}
