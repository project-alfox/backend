package com.ace.alfox.controllers;

import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionFactory;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.Database;
import org.dizitart.no2.NitriteId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
class ActionController {

    @Autowired
    private Database db;
    @Autowired
    private ActionFactory actionFactory;

    @RequestMapping(value = "/perform/{command}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity performCommand(HttpServletRequest request, @PathVariable String command, @RequestBody Map<String, Object> params) {
        HttpSession session = request.getSession(true);
        NitriteId playerId = (NitriteId) session.getAttribute("pid");

        if(playerId == null) {
            //return ResponseEntity.notFound().build();
            // Instead of just returning 404, create a test player
            Player injectedTestPlayer = new Player();
            db.players.insert(injectedTestPlayer);
            session.setAttribute("pid", injectedTestPlayer.id);
            playerId = injectedTestPlayer.id;
        }
        Player player = db.players.getById(playerId);
        IAction doSomething = actionFactory.get(command);
        ActionResult result = player.applyAction(doSomething, params);
        db.players.update(player);

        return ResponseEntity.ok(result);
    }
}