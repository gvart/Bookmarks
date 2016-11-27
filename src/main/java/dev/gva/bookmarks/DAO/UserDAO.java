package dev.gva.bookmarks.DAO;

import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.model.UserRole;

import java.util.List;
import java.util.Set;

/**
 * Created by pika on 10/17/16.
 */
public interface UserDAO {

    public void addUser(User u);
    public void updateUser(User u);
    public List<User> listUsers();
    public User getUserById(Integer id);
    public User getUserByUsername(String username);
    public void deleteUser(int id);
    public boolean userExists(String username);

}
