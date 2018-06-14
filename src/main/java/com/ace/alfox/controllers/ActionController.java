package com.ace.alfox.controllers;

import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionFactory;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.PlayerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.util.Map;

@RestController
class ActionController {

    @RequestMapping(value = "/perform/{command}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity home(HttpServletRequest request, @PathVariable String command, @RequestBody Map<String, Object> params) throws IOException {
        HttpSession session = request.getSession(true);
        String playerId = (String) session.getAttribute("cid");

        Player player = PlayerFactory.fetch(playerId);
        if(player == null) {
            return ResponseEntity.notFound().build();
        }

        ActionFactory doSomething = ActionFactory.named(command).withParameters(params);
        ActionResult result = player.applyAction(doSomething);
        PlayerFactory.save(result.player);

        return ResponseEntity.ok(result);
    }
}