package seon.gallery.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/reservation")
public class WriteController {
    
	/**
     * 리뷰 페이지
     * @return
     */
    @GetMapping("/reviews")
    public String reviews() {
    	
        return "/write/reviews";
    }

	/**
	 * 후기/질문쓰는 페이지
	 * @return
	 */
	@GetMapping("/write")
	public String write( ) {
		
		return "/write/write";
	}
	
	
}
