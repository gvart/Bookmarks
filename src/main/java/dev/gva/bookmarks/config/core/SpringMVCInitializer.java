package dev.gva.bookmarks.config.core;

import com.github.dandelion.core.web.DandelionFilter;
import com.github.dandelion.core.web.DandelionServlet;
import com.github.dandelion.datatables.core.web.filter.DatatablesFilter;
import dev.gva.bookmarks.config.AppConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;


/**
 * Created by pika on 8/26/16.
 */
public class SpringMVCInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        registerDandelionServlet(servletContext);
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
        characterEncodingFilter.setForceEncoding(true);

        // Dandelion filter definition and mapping -->
        DandelionFilter dandelionFilter = new DandelionFilter();

        // Dandelion-Datatables filter, used for basic export -->
        DatatablesFilter datatablesFilter = new DatatablesFilter();

        return new Filter[]{characterEncodingFilter, dandelionFilter, datatablesFilter};
    }

    @Override
    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
        FilterRegistration.Dynamic registration = super.registerServletFilter(servletContext, filter);
        registration.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "dandelionServlet");
        return registration;
    }

    private void registerDandelionServlet(ServletContext servletContext) {
        DandelionServlet dandelionServlet = new DandelionServlet();
        ServletRegistration.Dynamic registration = servletContext.addServlet("dandelionServlet", dandelionServlet);
        registration.setLoadOnStartup(2);
        registration.addMapping("/dandelion-assets/*");
        registration.setAsyncSupported(true);
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}
