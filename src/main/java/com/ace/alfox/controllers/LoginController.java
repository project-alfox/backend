package com.ace.alfox.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @RequestMapping("/login")
    public ResponseEntity login(@RequestParam String user, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("cid", "1234-1234-1234-1234");


        return ResponseEntity.ok().build();
    }
}
