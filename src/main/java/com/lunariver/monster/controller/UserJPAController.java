package com.lunariver.monster.controller;

import com.lunariver.monster.controller.bean.User;
import com.lunariver.monster.controller.bean.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jpa")
public class UserJPAController {

    private UserRepository userRepository;

    public UserJPAController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUser(){
        return userRepository.findAll();
    }
}
