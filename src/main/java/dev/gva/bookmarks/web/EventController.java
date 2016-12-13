package dev.gva.bookmarks.web;

import dev.gva.bookmarks.constants.Order;
import dev.gva.bookmarks.model.Event;
import dev.gva.bookmarks.model.EventType;
import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.service.AuthenticationService;
import dev.gva.bookmarks.service.EventService;
import dev.gva.bookmarks.service.EventTypeService;
import dev.gva.bookmarks.service.UserService;
import dev.gva.bookmarks.utils.DateParser;
import dev.gva.bookmarks.utils.GoogleMapsWorker;
import dev.gva.bookmarks.utils.UserFilesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private EventService eventService;
    private UserService userService;
    private EventTypeService eventTypeService;
    private UserFilesManager userFilesManager;
    private GoogleMapsWorker googleMapsWorker;

    @RequestMapping(path = "/event/create", method = RequestMethod.GET)
    public String init(ModelMap modelMap) {
        ArrayList<String> tags = eventTypeService.
                listOrderedEventTypes(Order.ASCENDENT).stream().
                map(EventType::getName).collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Integer> ageArr = new ArrayList<>();
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

    @RequestMapping(path = "/event/create", method = RequestMethod.POST)
    public String init(){
        //IDK how to fix it. form is send using ajax and after alert closing create another post request there!
        return "redirect:/";
    }


    @RequestMapping(path = "/event/registerEvent", method = RequestMethod.POST)
    public String createEvent(@ModelAttribute("event") Event event, @RequestParam("media") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
            User user = userService.getUserByUsername(AuthenticationService.getLoggedInUser());
            event.setCreateDate(new Date());
            event.setUser(user);
            event.setStreet(googleMapsWorker.getAddress(event.getLat(), event.getLng()));

            HashSet<EventType> eventsToAdd = new HashSet<>();
            //get all ET compare name of selected and add to set;
            for (EventType eventType : eventTypeService.listEventTypes()) {
                for (String et : request.getParameterValues("et")) {
                    if (et.equals(eventType.getName())) {
                        eventsToAdd.add(eventType);
                    }
                }
            }
            event.setEventTypes(eventsToAdd);
            event.setStartDate(new Date());
            //event.setStartDate(DateParser.parse(request.getParameter("startDate")));
            eventService.addEvent(event);
            logger.debug("Event " + event + "added.");

            response.setHeader("success","dsaasddsa");
            userFilesManager.uploadImage(event, user.getUsername(), file.getInputStream());
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

    @Autowired
    public void setUserFilesManager(UserFilesManager userFilesManager) {
        this.userFilesManager = userFilesManager;
    }

    @Autowired
    public void setGoogleMapsWorker(GoogleMapsWorker googleMapsWorker) {
        this.googleMapsWorker = googleMapsWorker;
    }
}
