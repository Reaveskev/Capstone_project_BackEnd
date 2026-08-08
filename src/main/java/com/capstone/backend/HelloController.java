package com.capstone.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/api/hello")
	public String hello() {
		return "Capstone Backend is running!";
	}

}
