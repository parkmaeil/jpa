package com.example.jpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    @GetMapping("/v1/user")
    public String user(){
        return "user";
    }

    @GetMapping("/v1/manager")
    public String manager(){
        return "manager";
    }

    @GetMapping("/v1/admin")
    public String admin(){
        return "admin";
    }
}
