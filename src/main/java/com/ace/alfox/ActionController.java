package com.ace.alfox;

import com.ace.alfox.game.GameCharacter;
import com.ace.alfox.lib.Action;
import com.ace.alfox.lib.CharacterFactory;
import com.ace.alfox.lib.Result;
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

        if(characterId == null) return ResponseEntity.notFound().build();
        if(params == null) return ResponseEntity.notFound().build();


        GameCharacter player = CharacterFactory.fetch(characterId);
        Action doSomething = Action.named(command).withParameters(params);
        Result result = player.applyAction(doSomething);
        CharacterFactory.save(result.character);
        return ResponseEntity.ok(result);
    }
}