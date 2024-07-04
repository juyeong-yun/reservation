package seon.gallery.reservation.controller;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.EventDTO;
import seon.gallery.reservation.dto.NoticeDTO;
import seon.gallery.reservation.dto.QnaDTO;
import seon.gallery.reservation.dto.ReserveDTO;
import seon.gallery.reservation.dto.ReviewDTO;
import seon.gallery.reservation.dto.check.YesorNo;
import seon.gallery.reservation.service.EventService;
import seon.gallery.reservation.service.NoticeService;
import seon.gallery.reservation.service.QnaService;
import seon.gallery.reservation.service.ReserveService;
import seon.gallery.reservation.service.ReviewService;
import seon.gallery.reservation.util.PageNevigator;

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

    @Value("${user.board.pageLimit}")
	int pageLimit = 5;


	/**
	 * board 로 가는
	 * @return
	 */
    @GetMapping("/board")
    public String board(Model model, @PageableDefault(page = 1) Pageable pageable) {
        
        Page<NoticeDTO> noticeList = noticeService.selectAll(pageable);

        int totalPages = (int) noticeList.getTotalPages();
		int page = pageable.getPageNumber();
        PageNevigator nevi = new PageNevigator(pageLimit, page, totalPages);


        model.addAttribute("noticeList", noticeList);
        model.addAttribute("nevi", nevi);

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
     * 예약 form 작성 페이지
     * @param eventId
     * @param model
     * @return
     */
    @GetMapping("/booking")
    public String booking(@RequestParam(name="eventId") String eventId, Model model){
        EventDTO event = eventService.selectOne(eventId);

        model.addAttribute("event", event);
        return "guest/booking";
    }

    /**
     * booking -> 폼 보내는
     * @param reserveDTO
     * @param attr
     * @return
     */
    @PostMapping("/reserveInsert")
    public String reserveInsert(@RequestParam(name = "eventId") String eventId, 
    @ModelAttribute ReserveDTO reserveDTO,  RedirectAttributes attr) {

        try {
            reserveDTO.setEventId(eventId);
            reserveService.reserveInsert(reserveDTO);
            attr.addFlashAttribute("message","예약 성공");
            return "redirect:/guest/location";

        } catch(Exception e) {
            attr.addFlashAttribute("message","예약 실패");
            return "redirect:/guest/reserve";
        }
    }

    @PostMapping("/searchReserve")
    public String searchReserve(@RequestParam(value = "reserver") String reserver,
    @RequestParam(value = "phone") String phone, Model model){

        List<ReserveDTO> dto = reserveService.searchReserver(reserver, phone);

        if (dto != null) {
            model.addAttribute("dto", dto);
        } else {
            log.info("검색되지 않음");
        }

        model.addAttribute("searchList", dto);

        return "guest/reserve";
    }
    


    
    /**
     * 들어오는 페이지에 따른 글쓰기 페이지
     * @param from
     * @param model
     * @return
     */
    @GetMapping("/write")
    public String write( Model model ) {

        return "guest/write";
    }
    
    @PostMapping("/writeInsert")
    public String writeInsert( @RequestParam(value = "detail", required = false) String detail,
    @ModelAttribute ReviewDTO reviewDTO, RedirectAttributes attr) {
        try {
            reviewDTO.setDetail(detail);
            reviewService.writeReview(reviewDTO);
            attr.addFlashAttribute("message", "리뷰 작성이 완료되었습니다."); 
            return "redirect:/guest/reviews";
        
        } catch (Exception e) {
            attr.addFlashAttribute("error", "리뷰 작성 중 오류가 발생했습니다.");
            log.error("리뷰 작성 중 오류 발생", e);
            return "redirect:/guest/reviews"; // 오류 발생 시에도 동일한 경로로 리다이렉트
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
     * 아이디의 리뷰 detail
     * @param reviewId
     * @param model
     * @return
     */
    @GetMapping("/reviewsDetail")
    public String reviewsDetail(@RequestParam(name="reviewId") Long reviewId, Model model) {

        ReviewDTO review = reviewService.selectOne(reviewId);

        model.addAttribute("review", review);
        return "/guest/reviewsDetail";
    }

    //========================= 첨부파일 다운로드 ======================
    @Value("${spring.servlet.multipart.location}")
    String uploadPath;
    
    
    
}
