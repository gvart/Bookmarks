package dev.gva.bookmarks.service;

import dev.gva.bookmarks.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pika on 11/9/16.
 */
@Service
public class AuthenticationService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        dev.gva.bookmarks.model.User user = userService.getUserByUsername(s);

        logger.info("Found user: " + user);

        if (user == null) {
            throw new UsernameNotFoundException("User " + s + " was not found in the database");
        }
        List<GrantedAuthority> authorities =
                buildUserAuthority(user.getUserRoles());

        return buildUserForAuthentication(user, authorities);
    }

    // org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(dev.gva.bookmarks.model.User user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet();

        // Build user's authorities
        for (UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        List<GrantedAuthority> Result = new ArrayList(setAuths);

        return Result;
    }

    public boolean autoLogin(String username, String password){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authenticationManager.authenticate(token);

        if(auth.isAuthenticated()) {
            logger.debug("Succeed to auth user: " + username);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return true;
        }

        return false;
    }
}
