package com.ace.alfox.game.models;

import com.ace.alfox.lib.ActionFactory;
import com.ace.alfox.lib.ActionWebResult;
import com.ace.alfox.lib.Vector2;

public class Player {
    public String id = "1234-1234-1234-1234";
    public String name = "Jimmy Fred";
    public int hp = 100;
    public Vector2 location = new Vector2(0,0);

    public ActionWebResult applyAction(ActionFactory doSomething) {
        // TODO: clone player
        return doSomething.action.applyAction(this, doSomething.params);
    }
}
