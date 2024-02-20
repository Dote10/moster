package com.lunariver.monster.controller;

import com.lunariver.monster.controller.bean.User;
import com.lunariver.monster.dao.UserDaoService;
import com.lunariver.monster.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public EntityModel<User> retrieveUser(@PathVariable int id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

           EntityModel entityModel = EntityModel.of(user);

        //this는 User클래스를 가르킨다.
        //
        WebMvcLinkBuilder linTo = linkTo(methodOn(this.getClass()).retrieveAllUser());
        entityModel.add(linTo.withRel("all-users"));  // all - users -> http://localhost:9000/users

        return entityModel;
    }

    //CREATE
    // input - detail of user
    // output - CREATE & RETURN the created URI
    @PostMapping("/users")
    public void createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
    }
}
