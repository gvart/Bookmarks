package dev.gva.bookmarks.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;



/**
 *
 *
 http://viralpatel.net/blogs/spring-4-mvc-tutorial-maven-example/
 The @Configuration annotation indicates that the class declares one or more @Bean methods. These methods are invoked at runtime by Spring to manage lifecycle of the beans. In our case we have defined @Bean for view resolver for JSP view.

 The @EnableWebMvc is equivalent to <mvc:annotation-driven /> in XML. It enables support for @Controller-annotated classes that use @RequestMapping or @GetMapping to map incoming requests to certain methods.

 The @ComponentScan annotation is equivalent to <context:component-scan> in XML. It will scan through the given package and register all the Controllers and beans.

 *
 */
@EnableWebMvc
@Configuration

@ComponentScan(basePackages = "dev.gva.bookmarks")
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("/messages/messages");
        return messageSource;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/vendors/**").addResourceLocations("/vendors/");
    }

    //Required when both servlet-mapping is '/' and static resources need to be served
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        simpleMappingExceptionResolver.setDefaultErrorView("exception");
        return simpleMappingExceptionResolver;
    }
}
