package dev.gva.bookmarks.DAO.DAOImpl;

import dev.gva.bookmarks.DAO.UserRoleDAO;
import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.model.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by pika on 11/10/16.
 */
@Repository
public class UserRoleDAOImpl implements UserRoleDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addRole(UserRole role) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(role);
    }

    @Override
    public void addRoles(Set<UserRole> roles) {
        roles.forEach( role -> addRole(role));
    }

    @Override
    public UserRole getRoleById(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        UserRole role = (UserRole) session.load(UserRole.class, new Integer(id));
        logger.info("User role loaded Successfully, Role details=" + role);
        return role;
    }

    @Override
    public void removeRole(UserRole role) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(role);
    }

    @Override
    public void removeRoles(Set<UserRole> roles) {
        Session session = this.sessionFactory.getCurrentSession();
        roles.forEach(role -> session.delete(role));
    }

}
