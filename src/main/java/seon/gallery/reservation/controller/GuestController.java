package seon.gallery.reservation.controller;

import org.hibernate.mapping.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.QnaDTO;
import seon.gallery.reservation.dto.ReviewDTO;
import seon.gallery.reservation.repository.QnaRepository;
import seon.gallery.reservation.service.EventService;
import seon.gallery.reservation.service.QnaService;
import seon.gallery.reservation.service.ReserveService;
import seon.gallery.reservation.service.ReviewService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@Slf4j
@RequestMapping("/reservation")
public class GuestController {

	private QnaService qnaService;
    private ReviewService reviewService;
    private ReserveService reserveService;
    private EventService eventService;

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
     * 들어오는 페이지에 따른 글쓰기 페이지
     * @param from
     * @param model
     * @return
     */
    @GetMapping("/write")
    public String write(
        @RequestParam(value="from", required = false) String from, Model model ) {

            if ("qna".equals(from)) {
                model.addAttribute("title", "QnA 글쓰기");
                model.addAttribute("detail","질문입니다.");
                
            } else if ("review".equals(from)) {
                model.addAttribute("title", "후기 쓰기");
                model.addAttribute("detail","후기입니다.");
            }

            model.addAttribute("from", from);
            
        return "/guest/write";
    }
    
    /**
     * 리뷰 / 질문글 작성
     * @param from
     * @param qnaDTO
     * @param reviewDTO
     * @param attr
     * @return
     */
    @PostMapping("/writeInsert")
    public String writeInsert(@RequestParam(value = "from", required = false) String from, 
        @ModelAttribute QnaDTO qnaDTO, @ModelAttribute ReviewDTO reviewDTO, RedirectAttributes attr
        ) {
            if ("qna".equals(from)) {
                qnaService.writeQna(qnaDTO);

            }else if ("review".equals(from)) {
                reviewService.writeReview(reviewDTO);
            }

            attr.addFlashAttribute("message", "글 작성이 완료되었습니다."); 
            // 리다이렉트 후 메시지 전달

        return "redirect:/guest/board";
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
