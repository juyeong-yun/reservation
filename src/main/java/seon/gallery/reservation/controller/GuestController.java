package seon.gallery.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.service.GuestService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
@RequestMapping("/reservation")
public class GuestController {
	private GuestService GuestService;

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
    
    /**
     * 예약 과정 페이지
     * @return
     */
    @GetMapping("/booking")
    public String booking() {
        return "/guest/booking";
    }
    
    /**
     * 글쓰기 페이지
     * @return
     */
    @GetMapping("/write")
    public String write() {
        return "/guest/write";
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
     * 리뷰 페이지에서 눌렀을때 해당하는 내용의 리뷰 세부페이지로 이동
     * 여기서는 확실히 @requestParam 필요
     * @return
     */
    @GetMapping("/reviewsDetail")
    public String reviewsDetail() {

        return "/guest/reviewsDetail";
    }
    
    
}
