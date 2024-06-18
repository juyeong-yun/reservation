package seon.gallery.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.service.GuestService;


@Controller
@Slf4j
@RequestMapping("/reservation")
public class GuestController {
	private GuestService guestService;

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
    @GetMapping("/reserve")
    public String reserve() {
    	
        return "/guest/reserve";
    }
    
    @GetMapping("/booking")
    public String booking() {
    	
    	return "/guest/booking";
    }
    
	/**
     * 리뷰 페이지
     * @return
     */
    @GetMapping("/reviews")
    public String reviews() {
    	
        return "/guest/reviews";
    }

	/**
	 * 후기/질문쓰는 페이지
	 * @return
	 */
	@GetMapping("/write")
	public String write( ) {
		
		return "/guest/write";
	}
    
    
    
    
}
