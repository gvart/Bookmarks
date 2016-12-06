package dev.gva.bookmarks.web;

import dev.gva.bookmarks.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.config.ExecutorBeanDefinitionParser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pika on 12/6/16.
 */
@ControllerAdvice
public class ExceptionHandlingController {

    private final static Logger logger = LoggerFactory.getLogger(ExecutorBeanDefinitionParser.class);


    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound404(HttpServletRequest request, Exception ex){
        logger.error("Request: " + request.getRequestURI() + " raised " + ex);

        return "404notFound";
    }


}
