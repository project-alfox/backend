package com.ace.alfox.lib;

import com.ace.alfox.game.GameCharacter;

import java.util.LinkedList;
import java.util.List;

public class Result {
    public List<String> message = new LinkedList<>();
    public GameCharacter character = null;
    public boolean ok = true;

    public Result(GameCharacter character) {
        this.character = character;
    }

    // TODO: make proper immutable
    public Result log(String s) {
        message.add(s);
        return this;
    }

    public Result notOk() {
        ok = false;
        return this;
    }
}
