package dev.gva.bookmarks.DAO.DAOImpl;

import dev.gva.bookmarks.DAO.UserDAO;
import dev.gva.bookmarks.model.User;
import dev.gva.bookmarks.model.UserRole;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.QueryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addUser(User u) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(u);
        logger.info("User saved successfully, User details=" + u);
    }

    @Override
    public void updateUser(User u) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(u);
        logger.info("User updated successfully, User details=" + u);
    }

    @Override
    public List<User> listUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> usersList = session.createQuery("from User").list();
        for(User u : usersList){
            logger.info("Users List::" + u);
        }
        return usersList;
    }

    @Override
    public User getUserById(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        User u = (User) session.load(User.class, new Integer(id));
        logger.info("User loaded Successfully, User details=" + u);
        return u;
    }

    @Override
    public User getUserByUsername(String username) {
        Session session = this.sessionFactory.getCurrentSession();
        User u = (User) session
                .createQuery("from User where username like ?")
                .setParameter(0,username)
                .uniqueResult();
        logger.info("User loaded Successfully, User details=" + u);
        return u;
    }

    @Override
    public void deleteUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User u = (User) session.load(User.class, new Integer(id));
        if(null != u) {
            session.delete(u);
        }
        logger.info("User deleted successfully, User details=" + u);
    }

    @Override
    public boolean userExists(String username){
        Session session = this.sessionFactory.getCurrentSession();

        User result = (User) session.
                createQuery("from User where username like ?")
                .setParameter(0,username)
                .uniqueResult();

        if(!(result == null)) return true;
        return false;
    }
}
