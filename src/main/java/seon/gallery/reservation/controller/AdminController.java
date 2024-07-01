package seon.gallery.reservation.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
	
	/**   
	 * 관리자 메인화면	
	 * @return
	 */
	@GetMapping("/adminMain")
	public String adminMain(Model model) {
		List<EventDTO> eventList = eventService.selectAll();

		model.addAttribute("eventList", eventList);
		
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
	 * 관리자 예약 확인 화면
	 * @return
	 */
	@GetMapping("/reserveCheck")
	public String reserveCheck(@ModelAttribute ReserveDTO reserveDTO //@RequestParam(value = "eventId", required = false) String eventId
	, Model model) {
		String eventId = reserveDTO.getEventId(); // reserveDTO에서 eventId 가져오기
    
		List<ReserveDTO> reserveList = reserveService.selectAll(eventId);
		log.info("Controller - eventId: {}", eventId);
		
		model.addAttribute("eventId", eventId);
		model.addAttribute("reserveList", reserveList);
		
		return "admin/reserveCheck";
	}
	

	/**
	 * 관리자 글작성 화면
	 * @return
	 */
	@GetMapping("/writeManage")
	public String writeManage(Model model) {
		List<QnaDTO> qnaList = qnaService.selectAll();
		List<NoticeDTO> noticeList = noticeService.selectAll();

		model.addAttribute("qnaList", qnaList);
		model.addAttribute("noticeList", noticeList);

		return "admin/writeManage";
	}

	@GetMapping("/adminWrite")
	public String adminWrite(){

		log.info("notice 글쓰기");
		
		return "admin/adminWrite";
	}

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
