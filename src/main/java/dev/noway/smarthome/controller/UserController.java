package dev.noway.smarthome.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dev.noway.smarthome.model.UserModel;
import dev.noway.smarthome.service.UserService;
import dev.noway.smarthome.utils.PassEncoding;
import dev.noway.smarthome.utils.Roles;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String root(Model model) {
        model.addAttribute("reqUser", new UserModel());
        logger.info("root");
        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("reqUser", new UserModel());
        logger.info("login");
        return "login";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        logger.info("home");
        return "home";
    }

    @RequestMapping("/admin")
    public String helloAdmin() {
        logger.info("admin");
        return "admin";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("reqUser", new UserModel());
        logger.info("register");
        return "register";
    }

    @RequestMapping(path = {"/user/register"}, method = RequestMethod.POST)
    public String register(@ModelAttribute("reqUser") UserModel reqUser, final RedirectAttributes redirectAttributes) {

        System.out.println("------------------------------------------\nt1");
        System.out.println("Email: " + reqUser.getEmail() + ", pass: " + reqUser.getPassword() + ", pass2: " +reqUser.getPassword_2());
        System.out.println("Redirect: " + redirectAttributes.toString());

        System.out.println("------------------------------------------");
        logger.info("/user/register");
        UserModel user = userService.findByEmail(reqUser.getEmail());
        if (user != null) {
            redirectAttributes.addFlashAttribute("saveUser", "exist-email");
            return "redirect:/register";
        }

        reqUser.setPassword(PassEncoding.getInstance().passwordEncoder.encode(reqUser.getPassword()));
        if (userService.findByRole(Roles.ROLE_ADMIN.getValue()).size() == 0) {
            reqUser.setRole(Roles.ROLE_ADMIN.getValue());
        } else {
            reqUser.setRole(Roles.ROLE_USER.getValue());
        }

        if (userService.save(reqUser) != null) {
            redirectAttributes.addFlashAttribute("saveUser", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveUser", "fail");
        }

        return "redirect:/register";
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = {"/api/login"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public UserModel apiLogin(UserModel user) {
        System.out.println("LOGIN: " + user.getEmail());
        if (userService.findByEmail(user.getEmail()) != null) {
            return userService.findByEmail(user.getEmail());
        } else {
            return null;
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = {"/api/register"}, method = RequestMethod.POST, consumes = "text/plain", produces = "application/json")
    public UserModel apiRegister(UserModel reqUser) {
        logger.info("/user/register");
        reqUser.setPassword(PassEncoding.getInstance().passwordEncoder.encode(reqUser.getPassword()));
        if (userService.findByRole(Roles.ROLE_ADMIN.getValue()).size() == 0) {
            reqUser.setRole(Roles.ROLE_ADMIN.getValue());
        } else {
            reqUser.setRole(Roles.ROLE_USER.getValue());
        }
        if (userService.save(reqUser) != null) {
            return userService.findByEmail(reqUser.getEmail());
        } else {
            return null;
        }
    }
}
