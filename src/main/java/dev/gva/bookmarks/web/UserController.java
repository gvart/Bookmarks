package dev.gva.bookmarks.web;

import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.model.UserRole;
import dev.gva.bookmarks.service.AuthenticationService;
import dev.gva.bookmarks.service.UserRoleService;
import dev.gva.bookmarks.service.UserService;
import dev.gva.bookmarks.utils.UserFilesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by pika on 10/17/16.
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private UserRoleService userRoleService;
    private AuthenticationService authenticationService;

    @Autowired
    private UserFilesManager userFilesManager;

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User u, BindingResult result, ModelMap modelMap,HttpServletRequest request) throws InterruptedException, IOException {
        boolean exists = userService.userExists(u.getUsername());
        String pwd = new BCryptPasswordEncoder().encode(u.getPassword());
        String tempPswd = u.getPassword();

        u.setCreateDate(new Date());
        u.setEnabled(true);
        u.setPassword(pwd);

        if(!result.hasErrors() && !exists) {
            userService.addUser(u);
            userRoleService.addRole(new UserRole(u,"ROLE_USER"));

            userFilesManager.createUserStore(u.getUsername());

            if(authenticationService.autoLogin(u.getUsername(),tempPswd)) {
                logger.debug("Succeed to auth user: " + u.getUsername());
                request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,SecurityContextHolder.getContext());
                return "redirect:/profile/" + u.getUsername();
            }
            logger.error("Failed to auth user: " + u);
            return "exception";

        }else {
            if(exists){
                modelMap.addAttribute("errorAuth", "User " +  u.getUsername() + " already exists.");
            }else {
                modelMap.addAttribute("errorAuth", result.getAllErrors().get(0).getDefaultMessage());
            }
            return "auth/login";
        }
    }


    @RequestMapping(value = "/profile/{username}")
    public String userProfile(@PathVariable(value = "username") String username, ModelMap modelMap,HttpServletRequest request){
            User user = userService.getUserByUsername(username);
            if(user == null){
                logger.debug("User " + username + " not found.");
                return "user/profilenotfound";
            }else {
                logger.debug("Found user " + username + ".");

                boolean master = false;
                Object principals =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                logger.debug(principals.toString() + " access " + username + " profile page.");

                if(!principals.toString().equals("anonymousUser")){
                    org.springframework.security.core.userdetails.User u =
                            (org.springframework.security.core.userdetails.User)
                                    principals;

                    if (username.equals(u.getUsername())) {
                        master = true;
                    }
                }
                //check if this is main profile
                modelMap.addAttribute("master", master);

                //check for background and profile images
                modelMap.addAttribute("wallImage", userFilesManager.getWallPhoto(username));
                modelMap.addAttribute("profileImage", userFilesManager.getProfilePhoto(username));
                modelMap.addAttribute("fullName", user.getFirstName() + " " + user.getLastName());
                modelMap.addAttribute("quote", user.getQuote());
                return "user/profile";
            }
    }


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
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

}
