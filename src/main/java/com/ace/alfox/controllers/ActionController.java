package com.ace.alfox.controllers;

import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionFactory;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.PlayerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.util.Map;

@RestController
class ActionController {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    private PlayerFactory playerFactory;

    @RequestMapping(value = "/perform/{command}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity home(HttpServletRequest request, @PathVariable String command, @RequestBody Map<String, Object> params) throws IOException {
        HttpSession session = request.getSession(true);
        String playerId = (String) session.getAttribute("cid");

        Player player = playerFactory.fetch(playerId);
        if(player == null) {
            return ResponseEntity.notFound().build();
        }

        ActionFactory doSomething = ActionFactory.named(command).withParameters(params);
        // Alright, so we've got the IAction in ActionFactory but, because we instantiated it Spring hasn't done any
        // dependency injection, so we're going to hint that it should do it now.
        beanFactory.autowireBean(doSomething.action);
        ActionResult result = player.applyAction(doSomething);
        playerFactory.save(result.player);

        return ResponseEntity.ok(result);
    }
}