package com.ace.alfox.controllers;

import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionFactory;
import com.ace.alfox.lib.ActionWebResult;
import com.ace.alfox.lib.CharacterFactory;
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
        String characterId = (String) session.getAttribute("cid");

        Player player = CharacterFactory.fetch(characterId);
        if(player == null) {
            return ResponseEntity.notFound().build();
        }

        ActionFactory doSomething = ActionFactory.named(command).withParameters(params);
        ActionWebResult result = player.applyAction(doSomething);
        CharacterFactory.save(result.character);

        return ResponseEntity.ok(result);
    }
}