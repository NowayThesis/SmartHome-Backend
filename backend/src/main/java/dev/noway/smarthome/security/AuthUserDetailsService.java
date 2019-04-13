package dev.noway.smarthome.security;

import org.springframework.stereotype.Service;
import dev.noway.smarthome.model.UserModel;
import dev.noway.smarthome.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthUserDetailsService.class);

    @Autowired
    private UserService userService;

    private org.springframework.security.core.userdetails.User springUser;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        UserModel user = getUserDetail(email);
        if (user != null) {
            springUser = new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities(user.getRole())
            );
            return springUser;
        } else {
            springUser = new org.springframework.security.core.userdetails.User("empty",
                    "empty",
                    false,
                    true,
                    true,
                    false,
                    getAuthorities(1)
            );
            return springUser;
        }
    }

    public List<GrantedAuthority> getAuthorities(Integer role) {

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        if (role == 1) {
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (role == 2) {
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authList;
    }

    private UserModel getUserDetail(String email) {

        UserModel user = userService.findByEmail(email);
        if (user == null) {
            logger.warn("user '" + email + "' on null!");
        } else {
            logger.info(user.toString());
        }
        return user;
    }
}
