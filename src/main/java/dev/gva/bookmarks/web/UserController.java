package dev.gva.bookmarks.web;

import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.model.UserRole;
import dev.gva.bookmarks.service.AuthenticationService;
import dev.gva.bookmarks.service.UserRoleService;
import dev.gva.bookmarks.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

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


    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User u, BindingResult result, ModelMap modelMap) throws InterruptedException {
        boolean exists = userService.userExists(u.getUsername());
        u.setCreateDate(new Date());
        u.setEnabled(true);
        String pwd = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(pwd);
        if(!result.hasErrors() && !exists) {
            userService.addUser(u);
            userRoleService.addRole(new UserRole(u,"ROLE_USER"));
            UserDetails userDetails = authenticationService.loadUserByUsername(u.getUsername());
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "index";
        }else {
            if(exists){
                modelMap.addAttribute("errorAuth", "User " +  u.getUsername() + " already exists.");
            }else {
                modelMap.addAttribute("errorAuth", result.getAllErrors().get(0).getDefaultMessage());
            }
            return "auth/login";
        }

    }
}
