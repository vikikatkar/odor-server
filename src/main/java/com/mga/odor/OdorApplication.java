package com.mga.odor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Add imports
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@RestController
public class OdorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdorApplication.class, args);
	}

  @GetMapping("/")
  public String hello() {
    return "hello milpitas!";
  }

}
