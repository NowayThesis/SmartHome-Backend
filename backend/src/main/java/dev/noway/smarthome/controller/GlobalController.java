package dev.noway.smarthome.controller;

import dev.noway.smarthome.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import dev.noway.smarthome.service.UserService;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GlobalController {

    @Autowired
    private UserService userService;

    private UserModel loginUser;

    public UserModel getLoginUser() {
        if (loginUser == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            loginUser = userService.findByEmail(auth.getName());
        }
        return loginUser;
    }

    public boolean isLogin() {
        if (loginUser == null) {
            return false;
        }
        return true;
    }
}
