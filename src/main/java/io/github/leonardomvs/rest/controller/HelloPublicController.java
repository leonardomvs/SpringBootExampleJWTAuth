package io.github.leonardomvs.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hellopublic")
public class HelloPublicController {
				
	@GetMapping
	public String hello() {				
		return "Hello. To see this message, you don't have to be authenticated.";
	}
	
}
