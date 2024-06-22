package seon.gallery.reservation.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.QnaDTO;
import seon.gallery.reservation.dto.ReviewDTO;
import seon.gallery.reservation.service.EventService;
import seon.gallery.reservation.service.QnaService;
import seon.gallery.reservation.service.ReserveService;
import seon.gallery.reservation.service.ReviewService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequestMapping("/reservation")
public class GuestController {

	private QnaService qnaService;
    private ReviewService reviewService;
    private ReserveService reserveService;
    private EventService eventService;

    public GuestController(QnaService qnaService){
        this.qnaService = qnaService;
    }

	/**
	 * board 로 가는
	 * @return
	 */
    @GetMapping("/board")
    public String board(HttpServletRequest request, Model model) {

        List<QnaDTO> qnaList = qnaService.selectAll();

        model.addAttribute("qnaList", qnaList);
        
        return "guest/board";
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

            if ("qna".equals(from)) {  log.info("qna 글쓰기");
            } else if ("review".equals(from)) { log.info("review 글쓰기"); }

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
        @ModelAttribute QnaDTO qnaDTO, @ModelAttribute ReviewDTO reviewDTO, 
        RedirectAttributes attr, BindingResult bindingResult ) {
            
            if (bindingResult.hasErrors()) {
                // 유효성 검사 실패 시 처리할 로직 추가
                return "errorPage"; // 예시: 유효성 검사 실패 페이지로 리다이렉트
            }

            if ("qna".equals(from)) {
                log.info("qnainsert");
                try {
                    qnaService.writeQna(qnaDTO);
                    attr.addFlashAttribute("message", "Q&A 작성이 완료되었습니다."); 
                    return "redirect:/guest/board";

                } catch (Exception e) {
                    attr.addFlashAttribute("error", "Q&A 작성 중 오류가 발생했습니다.");
                    log.error("Q&A 작성 중 오류 발생", e);
                }

            } else if ("review".equals(from)) {
                log.info("review insert");
                try {
                    reviewService.writeReview(reviewDTO);
                    attr.addFlashAttribute("message", "리뷰 작성이 완료되었습니다."); 

                } catch (Exception e) {
                    attr.addFlashAttribute("error", "리뷰 작성 중 오류가 발생했습니다.");
                    log.error("리뷰 작성 중 오류 발생", e);
                }

            } else {
                attr.addFlashAttribute("error", "잘못된 요청입니다.");
                log.warn("잘못된 요청: {}", from);
            }

        return "redirect:/reservation/board";
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
