package dev.gva.bookmarks.config;

import dev.gva.bookmarks.DAO.DAOImpl.EventDAOImpl;
import dev.gva.bookmarks.DAO.DAOImpl.EventTypeDAOImpl;
import dev.gva.bookmarks.DAO.DAOImpl.UserDAOImpl;
import dev.gva.bookmarks.DAO.DAOImpl.UserRoleDAOImpl;
import dev.gva.bookmarks.DAO.EventDAO;
import dev.gva.bookmarks.DAO.EventTypeDAO;
import dev.gva.bookmarks.DAO.UserDAO;
import dev.gva.bookmarks.DAO.UserRoleDAO;
import dev.gva.bookmarks.service.EventService;
import dev.gva.bookmarks.service.EventTypeService;
import dev.gva.bookmarks.service.ServiceImpl.EventServiceImpl;
import dev.gva.bookmarks.service.ServiceImpl.EventTypeServiceImpl;
import dev.gva.bookmarks.service.ServiceImpl.UserRoleServiceImpl;
import dev.gva.bookmarks.service.ServiceImpl.UserServiceImpl;
import dev.gva.bookmarks.service.UserRoleService;
import dev.gva.bookmarks.service.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by pika on 10/17/16.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({"dev.gva.bookmarks.config"})
@PropertySource(value = {"classpath:hibernate.properties"})
public class DatabaseConfig {


    @Autowired
    private Environment environment;


    @Bean
    public LocalSessionFactoryBean asessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[]{"dev.gva.bookmarks.model"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    public SessionFactory sessionFactory() {
        return asessionFactory().getObject();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }


    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }


    @Bean
    public UserDAO userDAO() {
        UserDAOImpl userDAO = new UserDAOImpl();
        userDAO.setSessionFactory((SessionFactory) sessionFactory());
        return userDAO;
    }

    @Bean
    public UserService userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(userDAO());
        return userService;
    }

    @Bean
    public UserRoleDAO userRoleDAO() {
        UserRoleDAOImpl userRoleDAO = new UserRoleDAOImpl();
        userRoleDAO.setSessionFactory(sessionFactory());
        return userRoleDAO;
    }

    @Bean
    public UserRoleService userRoleService() {
        UserRoleServiceImpl userRoleService = new UserRoleServiceImpl();
        userRoleService.setUserRoleDAO(userRoleDAO());
        return userRoleService;
    }

    @Bean
    public EventTypeDAO eventTypeDAO() {
        EventTypeDAOImpl eventTypeDAO = new EventTypeDAOImpl();
        eventTypeDAO.setSessionFactory(sessionFactory());
        return eventTypeDAO;
    }

    @Bean
    public EventTypeService eventTypeService() {
        EventTypeServiceImpl eventTypeService = new EventTypeServiceImpl();
        eventTypeService.setEventTypeDAO(eventTypeDAO());
        return eventTypeService;
    }

    @Bean
    public EventDAO eventDAO() {
        EventDAOImpl eventDAO = new EventDAOImpl();
        eventDAO.setSessionFactory(sessionFactory());
        return eventDAO;
    }

    @Bean
    public EventService eventService() {
        EventServiceImpl eventService = new EventServiceImpl();
        eventService.setEventDAO(eventDAO());
        return eventService;
    }
}
