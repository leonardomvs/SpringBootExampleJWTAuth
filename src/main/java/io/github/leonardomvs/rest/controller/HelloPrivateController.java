package io.github.leonardomvs.rest.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/helloprivate")
public class HelloPrivateController {
				
	@GetMapping
	public String hello() {				
		
		String userLogin = SecurityContextHolder.getContext().getAuthentication().getName(); 
		
		return new StringBuilder()
				.append("Hello.<br/>")
				.append("If you are seeing this message, that means you are authenticated with a valid JWT.<br/>")
				.append("Your email is: ").append(userLogin)
				.toString();
		
	}
	
}
