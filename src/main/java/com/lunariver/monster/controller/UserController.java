package com.lunariver.monster.controller;

import com.lunariver.monster.controller.bean.User;
import com.lunariver.monster.dao.UserDaoService;
import com.lunariver.monster.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUser(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }

    //CREATE
    // input - detail of user
    // output - CREATE & RETURN the created URI
    @PostMapping("/users")
    public void createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
    }
}
