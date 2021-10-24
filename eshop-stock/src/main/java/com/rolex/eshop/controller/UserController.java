/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rolex.eshop.model.User;
import com.rolex.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rolex
 * @since 2021
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/saveUser")
    public String saveUser(User user) throws JsonProcessingException {
        userService.save(user);
        return "success";
    }

    @GetMapping("/getUser")
    public User get(Long id) throws JsonProcessingException {
        return userService.findById(id);
    }
}
