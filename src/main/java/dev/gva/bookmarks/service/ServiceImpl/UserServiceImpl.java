package dev.gva.bookmarks.service.ServiceImpl;

import dev.gva.bookmarks.DAO.UserDAO;
import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pika on 10/17/16.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public void addUser(User u) {
        this.userDAO.addUser(u);
    }

    @Override
    @Transactional
    public void updateUser(User u) {
        this.userDAO.updateUser(u);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return this.userDAO.listUsers();
    }

    @Override
    @Transactional
    public User getUserById(Integer id) {
        return this.userDAO.getUserById(id);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        this.userDAO.deleteUser(id);
    }

    @Override
    @Transactional
    public boolean userExists(String username) {
        return this.userDAO.userExists(username);
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return this.userDAO.getUserByUsername(username);
    }
}
