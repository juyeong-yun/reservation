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
	
	@GetMapping("/admin_main")
	public String admin_main() {
		
		return "/admin/admin_main";
	}
	
	@GetMapping("/check")
	public String check() {
		return "/admin/check";
	}
	

}
