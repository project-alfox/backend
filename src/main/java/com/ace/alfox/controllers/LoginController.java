package com.ace.alfox.controllers;

import org.dizitart.no2.NitriteId;
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
        session.setAttribute("pid", NitriteId.createId(1234123412341234L));

        return ResponseEntity.ok().build();
    }
}
