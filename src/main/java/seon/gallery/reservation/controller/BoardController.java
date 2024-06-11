package seon.gallery.reservation.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
@RequestMapping("/guest")
public class BoardController {

    @GetMapping("/board")
    public String board() {
        log.info("어디니?");
        return "guest/board";
    }
    
    
}
