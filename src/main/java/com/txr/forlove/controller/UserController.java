package com.txr.forlove.controller;

import com.txr.forlove.domain.User;
import com.txr.forlove.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private Logger LOGGER = LogManager.getLogger(this.getClass());
    @Resource private UserService userService;

    @RequestMapping("/list")
    public String list(Model model){
        List<User> userList = userService.findAll();
        LOGGER.info("userList大小为：{}",userList.size());
        model.addAttribute("users",userList);
        return "userList";
    }
}
