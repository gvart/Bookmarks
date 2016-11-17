package dev.gva.bookmarks.service.ServiceImpl;

import dev.gva.bookmarks.DAO.UserRoleDAO;
import dev.gva.bookmarks.model.UserRole;
import dev.gva.bookmarks.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by pika on 11/10/16.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDAO userRoleDAO;

    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    @Override
    @Transactional
    public void addRole(UserRole role) {
        this.userRoleDAO.addRole(role);
    }

    @Override
    public void addRoles(Set<UserRole> roles) {
        this.userRoleDAO.addRoles(roles);
    }

    @Override
    @Transactional
    public UserRole getRoleById(Integer id) {
        return this.userRoleDAO.getRoleById(id);
    }

    @Override
    @Transactional
    public void removeRole(UserRole role) {
        this.userRoleDAO.removeRole(role);
    }

    @Override
    @Transactional
    public void removeRoles(Set<UserRole> roles) {
        this.userRoleDAO.removeRoles(roles);
    }
}
