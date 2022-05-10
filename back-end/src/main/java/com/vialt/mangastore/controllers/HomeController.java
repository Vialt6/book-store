package com.vialt.mangastore.controllers;

import com.vialt.mangastore.support.authentication.Utils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:4200/")
@RestController
public class HomeController {


    @GetMapping("/")
    @PreAuthorize("hasAuthority('user')")
    public String home(@RequestParam(value = "someValue") int value) {
        return "Welcome, " + Utils.getEmail() + " " + value + " !";
    }
}
