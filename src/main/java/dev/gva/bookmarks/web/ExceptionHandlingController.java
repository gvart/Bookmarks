package dev.gva.bookmarks.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.config.ExecutorBeanDefinitionParser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class ExceptionHandlingController implements HandlerExceptionResolver {

    private final static Logger logger = LoggerFactory.getLogger(ExecutorBeanDefinitionParser.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.error("ErrorLog: ", e);
        ModelAndView mov = new ModelAndView();
        mov.addObject("errorMsg", e.getMessage());
        mov.setViewName("error/exception");
        return mov;
    }
}
