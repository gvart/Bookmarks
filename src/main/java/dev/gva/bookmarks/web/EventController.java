package dev.gva.bookmarks.web;

import dev.gva.bookmarks.model.Event;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * Created by pika on 11/11/16.
 */
@Controller
public class EventController {

    @RequestMapping(path = "/event/create", method = RequestMethod.GET)
    public String create(ModelMap modelMap){

        ArrayList<String> privateArr = new ArrayList();
        privateArr.add("Yes");
        privateArr.add("No");

        ArrayList<Integer> ageArr = new ArrayList();
        ageArr.add(0);
        ageArr.add(6);
        ageArr.add(12);
        ageArr.add(16);
        ageArr.add(18);
        modelMap.addAttribute("event", new Event());
        modelMap.addAttribute("priv",privateArr);
        modelMap.addAttribute("age",ageArr);
        return "/event/create";
    }
}
