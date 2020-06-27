package com.example.EventRegistration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class EventRegistrationApplication {

  public static void main(String[] args) {
    SpringApplication.run(EventRegistrationApplication.class, args);
  }

  @RequestMapping("/")
  public String greeting(){
    return "Welcome to the backend base URL for the Event Registration Application. Use the API methods to interact with the backend.";
  }

}