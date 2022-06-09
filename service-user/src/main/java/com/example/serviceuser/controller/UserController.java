package com.example.serviceuser.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.common.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") String id){
        log.info("User id is {}", id);

        User user = new User();
        user.setName("User is " + id);
        return user;
    }
}

