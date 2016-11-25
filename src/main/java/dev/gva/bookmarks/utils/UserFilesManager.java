package dev.gva.bookmarks.utils;

import dev.gva.bookmarks.web.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

/**
 * Created by pika on 11/25/16.
 */
@PropertySource(value = {"classpath:config.properties"})
public class UserFilesManager {

    private static final Logger logger = LoggerFactory.getLogger(UserFilesManager.class);
    private final String DEFAULT_PROFILE_PHOTO_PATH = "/resources/images/defaultProfilePhoto.png";
    private final String DEFAULT_WALL_PHOTO_PATH = "/resources/images/defaultWallPhoto.png";

    @Autowired
    private Environment environment;
    private String rootPath = "";


    public void createUserStore(String username) throws IOException {
        initRootPath();
        final String userProfile  = rootPath + "/" + username + "/profile";
        final String userEvents  = rootPath + "/" + username + "/events";

        File f = new File(rootPath + "/" + username);
        if(f.mkdir()){
            logger.debug("Directory created for user:" + username);
            new File(userProfile).mkdir();
            new File(userEvents).mkdir();

            logger.debug("Finished to create directories for " + username);
        }
    }

    public void deleteUserStore(String username) throws IOException {
        initRootPath();
        final String userProfile  = rootPath + "/" + username + "/profile";
        final String userEvents  = rootPath + "/" + username + "/events";

        new File(userProfile).delete();
        new File(userEvents).delete();
        new File(rootPath + "/" + username).delete();

        logger.debug("Finished to delete user store" + username);
    }

    public String getProfilePhoto(String username){
        initRootPath();
        File photo = new File(rootPath + "/" + username + "/profile/");
        if(photo.exists()){
            logger.debug("Found user[" + username + "] profile folder");
            for (String s : photo.list()) {
                if(s.contains("profileImage")){
                    return "/userResources/" + username + "/profile/" + s;
                }
            }
        }else{
            logger.debug(photo.getAbsolutePath());
            logger.debug("Can't found user[" + username + "] profile folder");
        }
        return DEFAULT_PROFILE_PHOTO_PATH;
    }

    public String getWallPhoto(String username){
        initRootPath();
        File photo = new File(rootPath + "/" + username + "/profile/");
        if(photo.exists()) {
            logger.debug("Found user[" + username + "] profile folder");
            for (String s : photo.list()) {
                if (s.contains("wallImage")) {
                    return  "/userResources/" + username + "/profile/" + s;
                }
            }
        }else {
            logger.debug(photo.getAbsolutePath());
            logger.debug("Can't found user[" + username + "] profile folder");
        }
        return DEFAULT_WALL_PHOTO_PATH;
    }

    private void initRootPath(){
        if(rootPath.equals("")){
            rootPath = environment.getRequiredProperty("userFilePath");
        }
    }

}
