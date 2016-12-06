package dev.gva.bookmarks.DAO;

import dev.gva.bookmarks.model.User;

import java.util.List;

/**
 * Created by pika on 10/17/16.
 */
public interface UserDAO {

    void addUser(User u);

    void updateUser(User u);

    List<User> listUsers();

    User getUserById(Integer id);

    User getUserByUsername(String username);

    void deleteUser(int id);

    boolean userExists(String username);

}
