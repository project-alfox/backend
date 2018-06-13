package com.ace.alfox.game;

import com.ace.alfox.lib.Action;
import com.ace.alfox.lib.Result;
import com.ace.alfox.lib.Vector2;

public class GameCharacter {
    public String name = "Jimmy Fred";
    public int hp = 100;
    public Vector2 location = new Vector2(0,0);

    public Result applyAction(Action doSomething) {
        // TODO: clone player
        return doSomething.action.applyAction(this, doSomething.params);
    }
}
