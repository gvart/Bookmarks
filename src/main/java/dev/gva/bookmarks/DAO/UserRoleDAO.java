package dev.gva.bookmarks.DAO;

import dev.gva.bookmarks.model.UserRole;

import java.util.Set;

/**
 * Created by pika on 11/10/16.
 */
public interface UserRoleDAO {

    void addRole(UserRole role);

    void addRoles(Set<UserRole> roles);

    UserRole getRoleById(Integer id);

    void removeRole(UserRole role);

    void removeRoles(Set<UserRole> roles);
}
