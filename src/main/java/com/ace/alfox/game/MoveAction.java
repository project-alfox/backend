package com.ace.alfox.game;

import com.ace.alfox.lib.IAction;
import com.ace.alfox.lib.PlayerAction;
import com.ace.alfox.lib.Result;
import com.ace.alfox.lib.Vector2;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@PlayerAction(alias="move")
public class MoveAction implements IAction {
    HashMap<String, Vector2> directions = new HashMap<String, Vector2>() {{
        put("north", new Vector2( 0, 1));
        put("west",  new Vector2(-1, 0));
        put("east",  new Vector2( 1, 0));
        put("south", new Vector2( 0,-1));
    }};

    public MoveAction() {}

    @Override
    public Result applyAction(GameCharacter player, Map<String, Object> params) {
        Result result = new Result(player);
        String direction = (String) params.get("direction");

        if(player.hp == 0)
            return result.notOk().log("You're dead.");
        if(direction == null || !directions.containsKey(direction))
            return result.notOk().log("Unknown direction"); // notice .log returns `Result`

        // player.attacking.clear() // if they were attacking anyone, they aren't anymore
                                    // maybe do running away mechanic here?
        player.location = player.location.mathAdd(directions.get(direction));
        result.log("traveled " + direction);
        return result;
    }
}