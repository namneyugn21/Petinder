package com.petinder.notificationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification/email")
public class EmailController {
    @GetMapping("/send")
    public String sendEmail() {
        return "Email sent";
    }
}
