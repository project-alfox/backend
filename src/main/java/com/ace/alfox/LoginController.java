package com.ace.alfox;

import com.ace.alfox.game.GameCharacter;
import com.ace.alfox.lib.Database;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static org.fusesource.leveldbjni.JniDBFactory.bytes;

@RestController
public class LoginController {

    @RequestMapping("/login")
    public ResponseEntity login(@RequestParam String user, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("cid", "1234-1234-1234-1234");


        return ResponseEntity.ok().build();
    }
}
