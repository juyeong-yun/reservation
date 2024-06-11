package seon.gallery.reservation.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
    
    @GetMapping({"", "/"})
    public String main(){
        return "main";
    }
    

}
