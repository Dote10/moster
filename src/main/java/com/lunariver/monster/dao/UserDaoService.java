package com.lunariver.monster.dao;

import com.lunariver.monster.controller.bean.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int userCount = 3;

    static {
        users.add(new User(1,"Cess", new Date(),"test1", "111-111"));
        users.add(new User(2,"Alice", new Date(),"test2","222-222"));
        users.add(new User(3,"Elena ", new Date(), "test3", "333-333"));
    }

    public List<User> findAll(){
        return  users;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(++userCount);
        }

        if (user.getJoinDate() == null){
            user.setJoinDate(new Date());
        }

        users.add(user);

        return user;
    }

    public User findOne(int id){
        for (User user : users){
            if(user.getId()==id)return user;
        }
        return null;
    }
}
