package com.ace.alfox.lib;

import com.ace.alfox.game.models.Player;

import java.util.LinkedList;
import java.util.List;

public class ActionWebResult {
    public List<String> message = new LinkedList<>();
    public Player character = null;
    public boolean ok = true;

    public ActionWebResult(Player character) {
        this.character = character;
    }

    // TODO: make proper immutable
    public ActionWebResult log(String s) {
        message.add(s);
        return this;
    }

    public ActionWebResult notOk() {
        ok = false;
        return this;
    }
}
