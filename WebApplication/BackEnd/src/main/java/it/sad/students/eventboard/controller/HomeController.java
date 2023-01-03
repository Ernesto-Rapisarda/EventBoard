package it.sad.students.eventboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller

public class HomeController {
    @GetMapping("/")
    @ResponseBody
    public String home(){
        return "Hello,JWT";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "Hello Admin";
    }
}
