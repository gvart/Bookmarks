package dev.gva.bookmarks.web;

import dev.gva.bookmarks.model.EventType;
import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.model.UserRole;
import dev.gva.bookmarks.service.AuthenticationService;
import dev.gva.bookmarks.service.EventTypeService;
import dev.gva.bookmarks.service.UserRoleService;
import dev.gva.bookmarks.service.UserService;
import dev.gva.bookmarks.utils.UserFilesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

/**
 * Created by pika on 11/11/16.
 */
@Controller
public class AdminController {

    private UserService userService;
    private UserRoleService userRoleService;
    private EventTypeService eventTypeService;
    @Autowired
    private UserFilesManager userFilesManager;
    @Autowired
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Autowired
    @Qualifier(value = "userRoleService")
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    @Qualifier(value = "eventTypeService")
    public void setEventTypeService(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    @RequestMapping(value = "/admin/users/add", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute("user") User u, BindingResult result, ModelMap modelMap) throws InterruptedException {
        boolean exists = userService.userExists(u.getUsername());

        u.setCreateDate(new Date());
        u.setEnabled(true);

        if(!result.hasErrors() && !exists) {
            userService.addUser(u);
            userRoleService.addRole(new UserRole(u,"ROLE_USER"));
        }else {
            if(exists){
                modelMap.addAttribute("error", "User " +  u.getUsername() + " already exists.");
            }else {
                modelMap.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            }
        }
        return "redirect:/admin/users/listUsers";
    }

    @RequestMapping(path = "/admin/users/deleteUser", method = RequestMethod.GET)
    public String deleteUser(@RequestParam("id") int id,ModelMap modelMap) throws IOException {
        User user = userService.getUserById(id);
        userRoleService.removeRoles(user.getUserRoles());
        userService.removeUser(id);
        userFilesManager.deleteUserStore(user.getUsername());
        modelMap.addAttribute("user",user);
        return "info/showMessage";
    }

    @RequestMapping(path = "/admin/users/listUsers", method = RequestMethod.GET)
    public String listUsers(ModelMap modelMap){
        modelMap.addAttribute("allUsers", userService.listUsers());
        modelMap.addAttribute("user", new User());
        return "admin/user/listUsers";
    }

    /**
     *Event types mgmt
     *
     */

    @RequestMapping(path = "/admin/eventtype/listEventTypes", method = RequestMethod.GET)
    public String listEventTypes(ModelMap modelMap){
        modelMap.addAttribute("allEventTypes", eventTypeService.listEventTypes());
        modelMap.addAttribute("eventType", new EventType());
        return "admin/event/listEventTypes";
    }

    @RequestMapping(path = "/admin/eventtype/add", method = RequestMethod.POST)
    public String addEventType(@Valid @ModelAttribute("eventtype") EventType et, BindingResult result, ModelMap modelMap){

        if(!result.hasErrors()) {
            eventTypeService.addEventType(et);
        }else {
                modelMap.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
        }
        return "redirect:/admin/eventtype/listEventTypes";
    }

    @RequestMapping(path = "/admin/eventtype/delete", method = RequestMethod.GET)
    public String deleteEventType(@RequestParam("id") int id, ModelMap modelMap){
        EventType eventType = eventTypeService.findEventTypeById(id);
        eventTypeService.deleteEventType(eventType);
        return "admin/event/listEventTypes";
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String adminControlPanel(){
        return "admin/main";
    }
}
