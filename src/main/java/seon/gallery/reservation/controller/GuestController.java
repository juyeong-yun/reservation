package seon.gallery.reservation.controller;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.EventDTO;
import seon.gallery.reservation.dto.NoticeDTO;
import seon.gallery.reservation.dto.QnaDTO;
import seon.gallery.reservation.dto.ReviewDTO;
import seon.gallery.reservation.service.EventService;
import seon.gallery.reservation.service.NoticeService;
import seon.gallery.reservation.service.QnaService;
import seon.gallery.reservation.service.ReserveService;
import seon.gallery.reservation.service.ReviewService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Controller
@RequestMapping("/guest")
public class GuestController {

	private QnaService qnaService;
    private ReviewService reviewService;
    private NoticeService noticeService;
    private ReserveService reserveService;
    private EventService eventService;

    public GuestController(QnaService qnaService, ReviewService reviewService,
    ReserveService reserveService, EventService eventService, NoticeService noticeService){
        this.qnaService = qnaService;
        this.reviewService = reviewService;
        this.noticeService = noticeService;
        this.reserveService = reserveService;
        this.eventService = eventService;
    }


	/**
	 * board 로 가는
	 * @return
	 */
    @GetMapping("/board")
    public String board(HttpServletRequest request, Model model) {
        List<NoticeDTO> noticeList = noticeService.selectAll();
        List<QnaDTO> qnaList = qnaService.selectAll();

        model.addAttribute("noticeList", noticeList);
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
     * 찾아오는 페이지
     * @return
     */
    @GetMapping("/location")
    public String location() {
        return "/guest/location";
    }
    
    /**
     * 예약 페이지
     * @return
     */
    @GetMapping("/reserve")
    public String reserve(HttpServletRequest request, Model model) {
        List<EventDTO> eventList = eventService.selectAll();

        model.addAttribute("eventList", eventList);

        return "guest/reserve";
    }
    
    /**
     * 예약 과정 페이지
     * @return
     */
    @GetMapping("/booking")
    public String booking(@RequestParam(name="eventId") String eventId, Model model){
        EventDTO event = eventService.selectOne(eventId);

        model.addAttribute("event", event);
        return "guest/booking";
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
                log.info("qna 글쓰기");
            } else if ("review".equals(from)) { 
                log.info("review 글쓰기"); 
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
        @RequestParam(value = "detail", required = false) String detail,
        @ModelAttribute QnaDTO qnaDTO, 
        @ModelAttribute ReviewDTO reviewDTO,
        RedirectAttributes attr) {

            if ("qna".equals(from)) {
                
                try {
                    qnaDTO.setDetail(detail);
                    qnaService.writeQna(qnaDTO);
                    attr.addFlashAttribute("message", "Q&A 작성이 완료되었습니다."); 
                    return "redirect:/guest/board";
                
                } catch (Exception e) {
                    attr.addFlashAttribute("error", "Q&A 작성 중 오류가 발생했습니다.");
                    log.error("Q&A 작성 중 오류 발생", e);
                    return "redirect:/guest/board"; // 오류 발생 시에도 동일한 경로로 리다이렉트
                }
            } else if ("review".equals(from)) {
                
                try {
                    log.info("review:" + detail);
                    reviewDTO.setDetail(detail);
                    reviewService.writeReview(reviewDTO);
                    attr.addFlashAttribute("message", "리뷰 작성이 완료되었습니다."); 
                    return "redirect:/guest/reviews";

                } catch (Exception e) {
                    attr.addFlashAttribute("error", "리뷰 작성 중 오류가 발생했습니다.");
                    log.error("리뷰 작성 중 오류 발생", e);
                    return "redirect:/guest/reviews"; // 오류 발생 시에도 동일한 경로로 리다이렉트
                }
            } else {
                attr.addFlashAttribute("error", "잘못된 요청입니다.");
                log.warn("잘못된 요청: {}", from);
                return "redirect:/"; // 잘못된 요청의 경우 에러 페이지로 리다이렉트
            }
    }
    
    /**
     * 리뷰 페이지
     * @return
     */
    @GetMapping("/reviews")
    public String reviews(HttpServletRequest request, Model model) {
        
        List<ReviewDTO> reviewList = reviewService.selectAll();
        String contextPath = request.getContextPath();

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("contextPath", contextPath);

        return "guest/reviews";
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

    //========================= 첨부파일 다운로드 ======================
    @Value("${spring.servlet.multipart.location}")
    String uploadPath;
    
    
    
}
