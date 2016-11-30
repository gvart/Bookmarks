package dev.gva.bookmarks.web;

import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.model.UserRole;
import dev.gva.bookmarks.service.AuthenticationService;
import dev.gva.bookmarks.service.EmailService;
import dev.gva.bookmarks.service.UserRoleService;
import dev.gva.bookmarks.service.UserService;
import dev.gva.bookmarks.utils.UserFilesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pika on 10/17/16.
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private UserRoleService userRoleService;
    private AuthenticationService authenticationService;
    private UserFilesManager userFilesManager;
    private EmailService emailService;

    /**
     *
     * @param u entity that is binding to form in register view
     * @param result store all validation errors
     * @param modelMap is used to return error messages
     * @param request is used to authenticate user if registration occurred with success
     * @return home profile page if user was successfully validate or return registration page with error message
     * @throws IOException it may appear while creating user store folder
     */
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User u, BindingResult result, ModelMap modelMap,HttpServletRequest request) throws  IOException {
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
                //to send password how it is , not encrypted value
                u.setPassword(tempPswd);
                sendMailCredentials(u);
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

    /**
     *
     * @param username is a path variable which user page was accessed
     * @param modelMap is used to put all dates of 'username' user in it and use in view
     * @return profile page of 'username' user.
     */
    @RequestMapping(value = "/profile/{username}")
    public String userProfile(@PathVariable(value = "username") String username, ModelMap modelMap){
            User user = userService.getUserByUsername(username);
            if(user == null){
                logger.debug("User " + username + " not found.");
                return "user/profilenotfound";
            }else {
                logger.debug("Found user " + username + ".");

                boolean master = false;
                String tempUsername = getLoggedInUser();
                logger.debug(tempUsername + " access " + username + " profile page.");

                if(!tempUsername.equals("anonymousUser")){
                    if (username.equals(tempUsername)) {
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

    /**
     * @apiNote that method transform input image file in 'png' format and override previous profile images if it exists
     * @param file is new image file that will be set as a new profile image
     * @throws IOException
     */
    @RequestMapping(value = "/user/setProfilePhoto", method = RequestMethod.POST)
    public @ResponseBody String uploadProfilePhoto(@RequestParam("media") MultipartFile file) throws IOException {
        String user = getLoggedInUser();

        logger.debug("Start update profile photo for user:" + user);

        File dir = userFilesManager.getUserProfileDir(getLoggedInUser());
        userFilesManager.deleteUserProfilePhoto(user);

        // Create the file on server
        File serverFile = new File(dir.getAbsolutePath() + "/profileImage.png");
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        ImageIO.write(bufferedImage,"png",serverFile);
        bufferedImage.flush();
        return file.getSize() + " bytes";
    }

    /**
     * @apiNote that method transform input image file in 'png' format and override previous wall images if it exists
     * @param file is new image file that will be set as a new wall image
     * @throws IOException
     */
    @RequestMapping(value = "/user/setWallPhoto", method = RequestMethod.POST)
    public @ResponseBody void uploadWallPhoto(@RequestParam("media") MultipartFile file) throws IOException {
        String user = getLoggedInUser();

        logger.debug("Start update wall photo for user:" + user);

        File dir = userFilesManager.getUserProfileDir(getLoggedInUser());
        userFilesManager.deleteUserWallPhoto(user);

        // Create the file on server
        File serverFile = new File(dir.getAbsolutePath() + "/wallImage.png");

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        ImageIO.write(bufferedImage,"png",serverFile);
        bufferedImage.flush();
    }

    /**
     *
     * @return username from session , in case when user in not authenticated return will be 'anonymousUser'
     */
    private String getLoggedInUser(){
        Object principals =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!principals.toString().equals("anonymousUser")){
            org.springframework.security.core.userdetails.User u =
                    (org.springframework.security.core.userdetails.User)
                            principals;

            return u.getUsername();

        }else {
            return "anonymousUser";
        }
    }

    /**
     * @param u entity that contains information about user
     * @return true in case if email has send and false if it fails
     */
    private boolean sendMailCredentials(User u){
        Map<String, Object> model = new HashMap();

        model.put(EmailService.FROM,"bookmarks.software@gmail.com");
        model.put(EmailService.SUBJECT,"Congrats, now you are in Bookmarks community");
        model.put(EmailService.TO,new String[]{u.getEmail()});
        model.put("title","Congrats " + u.getFirstName() + " " + u.getLastName() + ", now you are in Bookmarks community.");
        model.put("message1","Username: " + u.getUsername() + "<br>Password: " + u.getPassword());
        model.put("message2","Platform link : <a href=\"http://bookmarks.com\">Bookmarks</a>");

        return emailService.sendMail("oneColumnTwoMessages.vm",model);
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
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setUserFilesManager(UserFilesManager userFilesManager) {
        this.userFilesManager = userFilesManager;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
