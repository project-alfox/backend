package com.ace.alfox.game;

import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionWebResult;
import com.ace.alfox.lib.PlayerAction;
import com.ace.alfox.lib.Vector2;

import java.util.HashMap;
import java.util.Map;

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
    public ActionWebResult applyAction(Player player, Map<String, Object> params) {
        ActionWebResult result = new ActionWebResult(player);
        String direction = (String) params.get("direction");

        if(player.hp == 0)
            return result.notOk().log("You're dead.");
        if(direction == null || !directions.containsKey(direction))
            return result.notOk().log("Unknown direction"); // notice .log returns `ActionWebResult`

        // player.attacking.clear() // if they were attacking anyone, they aren't anymore
                                    // maybe do running away mechanic here?
        player.location = player.location.add(directions.get(direction));
        result.log("traveled " + direction);
        return result;
    }
}