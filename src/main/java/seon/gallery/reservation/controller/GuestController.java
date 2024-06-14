package seon.gallery.reservation.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
@RequestMapping("/reservation")
public class GuestController {

	/**
	 * board 로 가는
	 * @return
	 */
    @GetMapping("/board")
    public String board() {
    	
        return "/guest/board";
    }
    
    /**
     * 전시소개 페이지 
     * @return
     */
    @GetMapping("/about")
    public String about() {
    	
        return "/guest/about";
    }
    
    /**
     * 예약 페이지
     * @return
     */
    @GetMapping("/reservation")
    public String reserve() {
    	
        return "/guest/reservation";
    }
    
    @GetMapping("/reviews")
    public String reviews() {
    	
        return "/write/reviews";
    }
    
}
