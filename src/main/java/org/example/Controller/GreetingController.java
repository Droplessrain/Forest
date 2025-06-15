package org.example.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @RequestMapping("/hellow")
    public String hellow(){
        return "Hellow it's Spring Boot с несколькими REST-контроллерами";
    }
}
