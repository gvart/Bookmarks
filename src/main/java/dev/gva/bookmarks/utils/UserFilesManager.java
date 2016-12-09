package dev.gva.bookmarks.utils;

import dev.gva.bookmarks.constants.ImageType;
import dev.gva.bookmarks.model.Event;
import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * Created by pika on 11/25/16.
 */
public class UserFilesManager {

    private static final Logger logger = LoggerFactory.getLogger(UserFilesManager.class);
    private final String DEFAULT_PROFILE_PHOTO_PATH = File.separator + "resources" + File.separator + "images" + File.separator + "defaultProfilePhoto.png";
    private final String DEFAULT_WALL_PHOTO_PATH = File.separator + "resources" + File.separator + "images" + File.separator + "defaultWallPhoto.png";

    @Autowired
    private Environment environment;

    private String rootPath = "";


    public void createUserStore(String username) throws IOException {
        initRootPath();
        final String userProfile = rootPath + File.separator + username + File.separator + "profile";
        final String userEvents = rootPath + File.separator + username + File.separator + "events";

        File f = new File(rootPath + File.separator + username);
        if (f.mkdir()) {
            logger.debug("Directory created for user:" + username);
            new File(userProfile).mkdir();
            new File(userEvents).mkdir();

            logger.debug("Finished to create directories for " + username);
        }
    }

    public void deleteUserStore(String username) throws IOException {
        initRootPath();
        final String userProfile = rootPath + File.separator + username + File.separator + "profile";
        final String userEvents = rootPath + File.separator + username + File.separator + "events";

        new File(userProfile).delete();
        new File(userEvents).delete();
        new File(rootPath + "/" + username).delete();

        logger.debug("Finished to delete user store" + username);
    }

    public String getProfilePhoto(String username) {
        initRootPath();
        File photo = new File(rootPath + File.separator + username + File.separator + "profile" + File.separator);
        if (photo.exists()) {
            logger.debug("Found user[" + username + "] profile folder");
            for (String s : photo.list()) {
                if (s.contains("profileImage")) {
                    return File.separator + "userResources" + File.separator + username + File.separator + "profile" + File.separator + s;
                }
            }
        } else {
            logger.debug(photo.getAbsolutePath());
            logger.debug("Can't found user[" + username + "] profile folder");
        }
        return DEFAULT_PROFILE_PHOTO_PATH;
    }

    public String getWallPhoto(String username) {
        initRootPath();
        File photo = new File(rootPath + File.separator + username + File.separator + "profile" + File.separator);
        if (photo.exists()) {
            logger.debug("Found user[" + username + "] profile folder");
            for (String s : photo.list()) {
                if (s.contains("wallImage")) {
                    return File.separator + "userResources" + File.separator + username + File.separator + "profile" + File.separator + s;
                }
            }
        } else {
            logger.debug(photo.getAbsolutePath());
            logger.debug("Can't found user[" + username + "] profile folder");
        }
        return DEFAULT_WALL_PHOTO_PATH;
    }

    private void initRootPath() {
        if (rootPath.equals("")) {
            rootPath = environment.getRequiredProperty("userFilePath");
        }
    }

    public File getUserProfileDir(String username) {
        initRootPath();
        return new File(rootPath + File.separator + username + File.separator + "profile");
    }

    public File getUserEventsDir(String username){
        initRootPath();
        return new File(rootPath + File.separator + username + File.separator + "events");
    }

    public String getExtension(String fileName) throws Exception {
        String f[] = fileName.split("\\.");

        logger.debug("After file split, Size: " + f.length);

        if (f.length == 2) {
            return f[1];
        } else if (f.length > 2) {
            return f[f.length - 1];
        } else {
            logger.debug("Can't found extension for '" + fileName + "'");
            throw new Exception("Can't found extension for '" + fileName + "'");
        }
    }

    public void deleteUserProfilePhoto(String username) {
        String profilePhoto = getProfilePhoto(username);
        if (!profilePhoto.equals(DEFAULT_PROFILE_PHOTO_PATH)) {
            new File(profilePhoto).delete();
        }
        logger.debug("Profile photo for user " + username + " is deleted.");
    }

    public void deleteUserWallPhoto(String username) {
        String wallPhoto = getWallPhoto(username);
        if (!wallPhoto.equals(DEFAULT_WALL_PHOTO_PATH)) {
            new File(DEFAULT_PROFILE_PHOTO_PATH).delete();
        }
        logger.debug("Profile photo for user " + username + " is deleted.");
    }

    public void uploadImage(ImageType type, String username, InputStream fileInputStream) throws IOException {
        logger.debug("Upload started. " + type + " image for user:" + username);


        File dir = null;
        String fileName = null;
        if(type.equals(ImageType.WALL_IMAGE)) {
            deleteUserWallPhoto(username);
            dir = getUserProfileDir(username);
            fileName = "wallImage.png";
        }else if(type.equals(ImageType.PROFILE_IMAGE)){
            deleteUserProfilePhoto(username);
            fileName = "profileImage.png";
            dir = getUserProfileDir(username);
        }else if (type.equals(ImageType.EVENT_IMAGE)){
            fileName = "";
            dir = getUserEventsDir(username);
        }

        // Create new file on server
        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
        BufferedImage bufferedImage = ImageIO.read(fileInputStream);
        ImageIO.write(bufferedImage, "png", serverFile);
        bufferedImage.flush();
    }

    public void uploadImage(Event event,String username, InputStream fileInputStream) throws IOException {
        logger.debug("Upload started. " + event.getName() + " image for user:" + username);

        File dir = getUserEventsDir(username);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        logger.debug("Input date: " + event.getDate());
        String fileName = event.getName() + " " + sdf.format(event.getDate()) + ".png";

        // Create new file on server
        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
        logger.debug("Image uploaded path: " + serverFile.getAbsolutePath());
        BufferedImage bufferedImage = ImageIO.read(fileInputStream);
        ImageIO.write(bufferedImage, "png", serverFile);
        bufferedImage.flush();
    }

   // public void addEventImage(User user,)
}
