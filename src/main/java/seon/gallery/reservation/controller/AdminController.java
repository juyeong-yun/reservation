package seon.gallery.reservation.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.EventDTO;
import seon.gallery.reservation.dto.EventFormDTO;
import seon.gallery.reservation.dto.NoticeDTO;
import seon.gallery.reservation.dto.QnaDTO;
import seon.gallery.reservation.dto.ReserveDTO;
import seon.gallery.reservation.entity.QnaEntity;
import seon.gallery.reservation.service.EventService;
import seon.gallery.reservation.service.NoticeService;
import seon.gallery.reservation.service.QnaService;
import seon.gallery.reservation.service.ReserveService;
import seon.gallery.reservation.util.PageNevigator;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

	private NoticeService noticeService;
	private EventService eventService;
	private QnaService qnaService;
	private ReserveService reserveService;

	public AdminController(NoticeService noticeService, EventService eventService,
	QnaService qnaService, ReserveService reserveService){
		this.noticeService = noticeService;
		this.eventService = eventService;
		this.qnaService = qnaService;
		this.reserveService = reserveService;
	}

	@Value("${user.board.pageLimit}")
	int pageLimit; 
	
	/**   
	 * 관리자 메인화면	
	 * @return
	 */
	@GetMapping("/adminMain")
	public String adminMain(Model model, @ModelAttribute EventDTO eventDTO) {

		List<EventDTO> eventList = eventService.selectAll();
		
		Map<LocalDate, List<EventDTO>> eventsByDate = eventList.stream().collect(Collectors.groupingBy(EventDTO::getEventDate));


		model.addAttribute("eventList", eventList);
		model.addAttribute("eventsByDate", eventsByDate);
		
		return "admin/adminMain";
	}

	/**
	 * event 추가 하는 
	 * @param eventDTO
	 * @param attr
	 * @return
	 */
	@PostMapping("/eventInsert")
	public String eventInsert(@ModelAttribute EventFormDTO formDTO, RedirectAttributes attr) {
		try {

			LocalDate eventDate = formDTO.getEventDate();
            LocalTime startTime = LocalTime.parse(formDTO.getStartTime());
            LocalTime endTime = LocalTime.parse(formDTO.getEndTime());

			log.info("event 등록중"); 
			while (!startTime.isAfter(endTime)) {
				// 30분 간격으로 이벤트 생성
				EventDTO eventDTO = new EventDTO();
				eventDTO.setEventDate(eventDate);
				
				// 다시 문자열로 만들기
				String formatter = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
				eventDTO.setEventTime(formatter);

				eventService.eventInsert(eventDTO);

				startTime = startTime.plusMinutes(30);
			}
			attr.addFlashAttribute("message","event 등록 성공");
			return "redirect:/admin/adminMain";

		} catch (Exception e) {
			attr.addFlashAttribute("error", "event 등록 중 오류가 발생했습니다.");
			log.error("event 등록 중 오류 발생", e);
			return "redirect:/admin/adminMain"; // 오류 발생 시에도 동일한 경로로 리다이렉트

		}
	}

	/**
	 * 관리자 예약관리 화면
	 * @return
	 */
	@GetMapping("/reserveCheck")
	public String reserveCheck(Model model, 
	@RequestParam(value = "searchWord", required = false) String searchWord,
	@PageableDefault(page = 1) Pageable pageable) {

		// List<ReserveDTO> reserveList = reserveService.selectAll();
		Page<ReserveDTO> reserveList = reserveService.selectAll(pageable, searchWord);

		int totalPages = (int) reserveList.getTotalPages();
		int page = pageable.getPageNumber();

		PageNevigator nevi = new PageNevigator(pageLimit, page, totalPages);

		model.addAttribute("reserveList", reserveList);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("nevi", nevi);
		
		return "admin/reserveCheck";
	}
	
	/**
	 * 
	 * @param reserveId
	 * @return
	 */
	@PostMapping("/reservationProc")
	public String reservationProc(@RequestParam(value = "reserveId", required = false) Long reserveId, 
	@RequestParam(value = "action", required = false) String action, RedirectAttributes attr){
		
		if ("complete".equals(action)) {
			try{
				// ReserveDTO reserveDTO = reserveService.selectOne(reserveId);
				reserveService.completeReservation(reserveId);
				attr.addFlashAttribute("message", "예약 완료 처리");

			} catch(Exception e) {
				log.info("입금완료 처리중 오류 발생");
			}
				
		} else if("cancel".equals(action)) {
			try {
				reserveService.cancelReservation(reserveId);
				attr.addFlashAttribute("message", "예약 취소 처리");
			} catch (Exception e) {
				log.info("예약 취소 중 오류 발생");
			}
		} 
            
		return "redirect:/admin/reserveCheck";
	}

	/**
	 * 관리자 글작성 화면
	 * @return
	 */
	@GetMapping("/writeManage")
	public String writeManage(Model model, @Qualifier("q_pageable")@PageableDefault(page = 1) Pageable q_pageable,
    @Qualifier("n_pageable")@PageableDefault(page = 1) Pageable n_pageable ) {

		Page<NoticeDTO> noticeList = noticeService.selectAll(n_pageable);
        Page<QnaDTO> qnaList = qnaService.selectAll(q_pageable);
        // List<NoticeDTO> noticeList = noticeService.selectAll();
        // List<QnaDTO> qnaList = qnaService.selectAll();

        int n_totalPages = (int) noticeList.getTotalPages();
        int q_totalPages = (int) qnaList.getTotalPages();
		int n_page = n_pageable.getPageNumber();
		int q_page = q_pageable.getPageNumber();

        PageNevigator n_nevi = new PageNevigator(pageLimit, n_page, n_totalPages);
        PageNevigator q_nevi = new PageNevigator(pageLimit, q_page, q_totalPages);

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("qnaList", qnaList);
        model.addAttribute("qnevi", q_nevi);
        model.addAttribute("nnevi", n_nevi);

		return "admin/writeManage";
	}

	/**
	 * 글쓰기 화면
	 * @return
	 */
	@GetMapping("/adminWrite")
	public String adminWrite(){

		log.info("notice 글쓰기");
		
		return "admin/adminWrite";
	}

	/**
	 * 공지 글쓰기
	 * @param details
	 * @param noticeDTO
	 * @param attr
	 * @return
	 */
	@PostMapping("/noticeWrite")
	public String noticeWrite(@RequestParam(value = "detail", required = false) String details,
	@ModelAttribute NoticeDTO noticeDTO,RedirectAttributes attr) {
		
		try{
			noticeDTO.setDetail(details);
			noticeService.writeNotice(noticeDTO);
			attr.addFlashAttribute("message", "notice 작성 완료");
			return "redirect:/admin/writeManage";
		
		} catch (Exception e) {
			attr.addFlashAttribute("message", "notice 작성 중 오류가 발생했습니다.");
            log.error("notice 작성 중 오류 발생", e);
            return "redirect:/admin/writeManage"; // 오류 발생 시에도 동일한 경로로 리다이렉트
		}
	}

	/**
	 * @ResponseBody를 사용하여 컨트롤러가 반환하는 데이터를 HTTP 응답의 본문으로 변환하여 클라이언트에게 전송할 수 있습니다.
	 */

	/**
	 * ajax 로 notice  detail 보기
	 * @param noticeId
	 * @return
	 */
	@GetMapping("/noticeDetail")
	@ResponseBody
	public NoticeDTO noticeDetail(@RequestParam(name = "noticeId") Long noticeId) {
		NoticeDTO noticeDTO = noticeService.selectOne(noticeId);

		return noticeDTO;
	} 

	/**
	 * ajax 로 qna detail 보기
	 * @param qnaId
	 * @return
	 */
	@GetMapping("/qnaDetail")
	@ResponseBody
	public QnaDTO qnaDetail(@RequestParam(name = "qnaId")Long qnaId) {
		QnaDTO qnaDTO = qnaService.selectOne(qnaId);
		log.info("DTO" +qnaDTO);
		return qnaDTO;
	}	
	

}
