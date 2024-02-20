package com.lunariver.monster.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.lunariver.monster.controller.bean.AdminUser;
import com.lunariver.monster.controller.bean.AdminUserV2;
import com.lunariver.monster.controller.bean.User;
import com.lunariver.monster.dao.UserDaoService;
import com.lunariver.monster.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service){
        this.service = service;
    }

    //@GetMapping("/v1/users/{id}")
    //@GetMapping(value = "users/{id}", version = "version=1")
    //@GetMapping(value = "users/{id}", headers = "X-API-VERSION=1")
    @GetMapping(value = "users/{id}", produces = "application/vnd.company.app1+json")
    public MappingJacksonValue retrieveUserAdmin(@PathVariable int id){
        User user = service.findOne(id);

        //1. 단순하게 검색되 데이터를 반환해주는 것이 아니다.
        AdminUser adminUser = new AdminUser();
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }else{
            BeanUtils.copyProperties(user,adminUser);
        }

        //2. SimpleBeanPropertyFilter를 사용
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping =  new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}", param = "version=2")
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "users/{id}", produces = "application/vnd.company.app2+json")
    public MappingJacksonValue retrieveUserAdminV2(@PathVariable int id){
        User user = service.findOne(id);


        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        AdminUserV2 adminUserV2 = new AdminUserV2();
        BeanUtils.copyProperties(user,adminUserV2);
        adminUserV2.setGrade("VIP"); //grade

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mapping =  new MappingJacksonValue(adminUserV2);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsersAdmin(){
        List<User> users = service.findAll();

        //1. 단순하게 검색되 데이터를 반환해주는 것이 아니다.
        List<AdminUser> adminUsers = new ArrayList<>();
        AdminUser adminUser = null;

        for(User user: users){
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user,adminUser);
            adminUsers.add(adminUser);
        }

        //2. SimpleBeanPropertyFilter를 사용
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping =  new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }

}
