package seon.gallery.reservation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.NoticeDTO;
import seon.gallery.reservation.dto.QnaDTO;
import seon.gallery.reservation.entity.QnaEntity;
import seon.gallery.reservation.service.EventService;
import seon.gallery.reservation.service.NoticeService;
import seon.gallery.reservation.service.QnaService;
import seon.gallery.reservation.service.ReserveService;
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
	
	/**   
	 * 관리자 메인화면	
	 * @return
	 */
	@GetMapping("/adminMain")
	public String adminMain() {
		
		return "/admin/adminMain";
	}
	
	/**
	 * 관리자 예약 확인 화면
	 * @return
	 */
	@GetMapping("/reserveCheck")
	public String reserveCheck() {
		return "/admin/reserveCheck";
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
