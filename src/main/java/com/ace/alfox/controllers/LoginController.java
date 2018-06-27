package com.ace.alfox.controllers;

import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.Database;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.dizitart.no2.NitriteId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

  @Autowired private Database db;

  @GetMapping("/login")
  public ResponseEntity getLoginStatus(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    NitriteId playerId = (NitriteId) session.getAttribute("pid");
    if (playerId == null) {
      return ResponseEntity.status(401).build();
    } else {
      Player player = db.players.getById(playerId);
      return ResponseEntity.status(200).body(player);
    }
  }

  @PostMapping("/login")
  public ResponseEntity login(@RequestParam String user, HttpServletRequest request) {
    HttpSession session = request.getSession(true);

    // TODO: Lookup user and verify credentials
    // TODO: Lookup `Player` for given authenticated user and set the `pid` (PlayerId) in the active
    // session.

    // For now because we don't have a user database yet, just use the first 8 bytes of the username
    // as the playerId
    // and create a player if it doesn't exist.
    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    buffer.put(Arrays.copyOfRange(user.getBytes(), 0, Long.BYTES));
    buffer.flip(); // reset position to 0
    NitriteId playerId = NitriteId.createId(buffer.getLong());
    session.setAttribute("pid", playerId);

    Player p = db.players.getById(playerId);
    if (p == null) {
      p = new Player();
      p.id = playerId;
      p.name = user;
      db.players.insert(p);
    }

    return ResponseEntity.ok().build();
  }
}
