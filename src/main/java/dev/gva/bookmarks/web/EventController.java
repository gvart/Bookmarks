package dev.gva.bookmarks.web;

import dev.gva.bookmarks.model.Event;
import dev.gva.bookmarks.model.EventType;
import dev.gva.bookmarks.service.AuthenticationService;
import dev.gva.bookmarks.service.EventService;
import dev.gva.bookmarks.service.EventTypeService;
import dev.gva.bookmarks.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pika on 11/11/16.
 */
@Controller
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private EventService eventService;
    private UserService userService;
    private EventTypeService eventTypeService;

    @RequestMapping(path = "/event/create", method = RequestMethod.GET)
    public String init(ModelMap modelMap) {
        ArrayList<String> tags = eventTypeService.listEventTypes().stream().map(EventType::getName).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> ageArr = new ArrayList();
        ageArr.add(0);
        ageArr.add(6);
        ageArr.add(12);
        ageArr.add(16);
        ageArr.add(18);
        modelMap.addAttribute("event", new Event());
        modelMap.addAttribute("age", ageArr);
        modelMap.addAttribute("eventTypes", tags);
        return "/event/create";
    }


    @RequestMapping(path = "/event/registerEvent", method = RequestMethod.POST)
    public String createEvent(@ModelAttribute("event") Event event,@Param("eventTypes") ArrayList<String> eventTypes) {
        /*event.setDate(new Date());
        event.setUser(userService.getUserByUsername(
                AuthenticationService.getLoggedInUser())пше
        );
        eventService.addEvent(event);*/

        return "redirect:/";
    }


    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEventTypeService(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }
}
