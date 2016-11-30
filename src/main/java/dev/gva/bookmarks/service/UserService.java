package dev.gva.bookmarks.service;

import dev.gva.bookmarks.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pika on 10/10/16.
 */

public interface UserService {

    void addUser(User u);
    void updateUser(User u);
    List<User> listUsers();
    User getUserById(Integer id);
    User getUserByUsername(String username);
    void deleteUser(int id);
    boolean userExists(String username);
}
