package com.ace.alfox.controllers;

import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.data.Database;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  @Autowired private Database db;

  @GetMapping("/login")
  public ResponseEntity getLoginStatus(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Long playerId = (Long) session.getAttribute("pid");
    if (playerId == null) {
      return ResponseEntity.status(401).build();
    } else {
      Player player = db.players.find(playerId);
      return ResponseEntity.status(200).body(player);
    }
  }

  @PostMapping("/login")
  public ResponseEntity login(@RequestParam String user, HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Player p = db.players.find(user);
    if (p == null) {
      /* this will never happen until Adam rewrites `PlayerRepository.find` to <u>not</u> auto-create test accounts. */
      return ResponseEntity.notFound().build();
    }

    // TODO: Lookup user and verify credentials

    session.setAttribute("pid", p.id);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/signup")
  public ResponseEntity signup(HttpServletRequest request) {
    // TODO implement user signup
    return ResponseEntity.noContent().build();
  }
}
