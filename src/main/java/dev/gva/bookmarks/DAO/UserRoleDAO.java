package dev.gva.bookmarks.DAO;

import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.model.UserRole;

import java.util.Set;

/**
 * Created by pika on 11/10/16.
 */
public interface UserRoleDAO {

    public void addRole(UserRole role);
    public void addRoles(Set<UserRole> roles);
    public UserRole getRoleById(Integer id);
    public void removeRole(UserRole role);
    public void removeRoles(Set<UserRole> roles);
}
