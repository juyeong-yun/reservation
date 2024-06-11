package seon.gallery.reservation.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
@RequestMapping("/guest")
public class GuestController {

    @GetMapping("/board")
    public String board() {
    	
        return "guest/board";
    }
    
    
}
