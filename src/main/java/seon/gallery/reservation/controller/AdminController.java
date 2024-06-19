package seon.gallery.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.service.AdminService;

@Controller
@Slf4j
@RequestMapping("/reservation")
public class AdminController {
	private static AdminService adminService;
	
	/**   
	 * 관리자 메인화면	
	 * @return
	 */
	@GetMapping("/admin_main")
	public String admin_main() {
		
		return "/admin/admin_main";
	}
	
	/**
	 * 관리자 예약 확인 화면
	 * @return
	 */
	@GetMapping("/check")
	public String check() {
		return "/admin/check";
	}
	
	/**
	 * 관리자 관리화면
	 * @return
	 */
	@GetMapping("/manage")
	public String manage() {
		return "/admin/manage";
	}

	/**
	 * 관리자 글작성 화면
	 * @return
	 */
	@GetMapping("/admin_write")
	public String admin_write() {
		return "/admin/admin_write";
	}
	

}
