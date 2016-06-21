package br.com.challenge2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloEndpoint {

    @RequestMapping("/")
    public String hello() {
        return "It works!";
    }

}
