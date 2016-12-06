package dev.gva.bookmarks.web;

import dev.gva.bookmarks.model.EventType;
import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.model.UserRole;
import dev.gva.bookmarks.service.EmailService;
import dev.gva.bookmarks.service.EventTypeService;
import dev.gva.bookmarks.service.UserRoleService;
import dev.gva.bookmarks.service.UserService;
import dev.gva.bookmarks.utils.UserFilesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private UserService userService;
    private UserRoleService userRoleService;
    private EventTypeService eventTypeService;
    private UserFilesManager userFilesManager;
    private EmailService emailService;


    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setUserFilesManager(UserFilesManager userFilesManager) {
        this.userFilesManager = userFilesManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setEventTypeService(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    @RequestMapping(value = "/admin/users/add", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute("user") User u, BindingResult result, ModelMap modelMap) throws InterruptedException {
        boolean exists = userService.userExists(u.getUsername());

        u.setCreateDate(new Date());
        u.setEnabled(true);

        if (!result.hasErrors() && !exists) {
            userService.addUser(u);
            userRoleService.addRole(new UserRole(u, "ROLE_USER"));
        } else {
            if (exists) {
                modelMap.addAttribute("error", "User " + u.getUsername() + " already exists.");
            } else {
                modelMap.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            }
        }
        return "redirect:/admin/users/listUsers";
    }

    @RequestMapping(path = "/admin/users/deleteUser", method = RequestMethod.GET)
    public String deleteUser(@RequestParam("id") int id, ModelMap modelMap) throws IOException {
        User user = userService.getUserById(id);
        userRoleService.removeRoles(user.getUserRoles());
        userService.deleteUser(id);
        userFilesManager.deleteUserStore(user.getUsername());
        modelMap.addAttribute("user", user);
        return "info/showMessage";
    }

    @RequestMapping(path = "/admin/users/listUsers", method = RequestMethod.GET)
    public String listUsers(ModelMap modelMap) {
        modelMap.addAttribute("allUsers", userService.listUsers());
        modelMap.addAttribute("user", new User());
        return "admin/user/listUsers";
    }

    /**
     * Event types mgmt
     */

    @RequestMapping(path = "/admin/eventtype/listEventTypes", method = RequestMethod.GET)
    public String listEventTypes(ModelMap modelMap) {
        logger.debug("INPARAM##########:" + modelMap.get("error"));
        modelMap.addAttribute("allEventTypes", eventTypeService.listEventTypes());
        modelMap.addAttribute("eventType", new EventType());
        return "admin/event/listEventTypes";
    }

    @RequestMapping(path = "/admin/eventtype/add", method = RequestMethod.POST)
    public String addEventType(@Valid @ModelAttribute("eventtype") EventType et, BindingResult result, ModelMap modelMap) {
        if (result.hasErrors() || eventTypeService.findEventTypeByName(et.getName()) != null) {
            //result.getAllErrors().get(0).getDefaultMessage()
            modelMap.addAttribute("error", "Inserted value is not valid.");
        } else {
            eventTypeService.addEventType(et);
        }
        return "redirect:/admin/eventtype/listEventTypes";
    }

    @RequestMapping(path = "/admin/eventtype/delete", method = RequestMethod.GET)
    public String deleteEventType(@RequestParam("id") int id, ModelMap modelMap) {
        EventType eventType = eventTypeService.findEventTypeById(id);
        eventTypeService.deleteEventType(eventType);
        return "redirect:/admin/eventtype/listEventTypes";
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String adminControlPanel() {
        return "admin/main";
    }

    //MAIL

   /* @RequestMapping(value = "/email/send", method = RequestMethod.GET)
    public @ResponseBody String sendMail(){
        logger.debug("Start send mail.");
        Map<String, Object> model = new HashMap();
        model.put(EmailService.FROM,"bookmarks.software@gmail.com");
        model.put(EmailService.SUBJECT,"testmail");
        model.put(EmailService.TO,new String[]{"gladis_vlad@yahoo.com","sho00t@bk.ru"});
        model.put("title","Welcome in our community");
        model.put("message1","test123213213");
        model.put("message2 ","hello world");
        boolean result = emailService.sendMail("single-column.vm",model);

        return "Your message sent status: " + result;
    }*/

}
