package dev.gva.bookmarks.service;

import dev.gva.bookmarks.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pika on 10/10/16.
 */

public interface UserService {

    public void addUser(User u);
    public void updateUser(User u);
    public List<User> listUsers();
    public User getUserById(Integer id);
    public User getUserByUsername(String username);
    public void deleteUser(int id);
    public boolean userExists(String username);
}
