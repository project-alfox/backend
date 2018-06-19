package com.ace.alfox.game;

import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.game.models.Location;
import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.dizitart.no2.objects.filters.ObjectFilters.*;

@PlayerAction(alias="move")
public class MoveAction implements IAction {
    HashMap<String, Vector2> directions = new HashMap<String, Vector2>() {{
        put("north", new Vector2( 0, 1));
        put("west",  new Vector2(-1, 0));
        put("east",  new Vector2( 1, 0));
        put("south", new Vector2( 0,-1));
    }};

    Database db;
    @Autowired public MoveAction(Database db) {
        this.db = db;
    }

    @Override
    public ActionResult applyAction(Player player, Map<String, Object> params) {
        ActionResult result = new ActionResult(player);
        String direction = (String) params.get("direction");

        if(player.hp == 0)
            return result.notOk().log("You're dead.");
        if(direction == null || !directions.containsKey(direction))
            return result.notOk().log("Unknown direction"); // notice .log returns `ActionResult`

        // player.attacking.clear() // if they were attacking anyone, they aren't anymore
                                    // maybe do running away mechanic here?
        player.location = player.location.add(directions.get(direction));
        result.log("traveled " + direction);

        Location location = db.locations.find(eq("coordinates", player.location)).firstOrDefault();
        if(location == null) {
            location = new Location();
        }
        result.log("You see, " + location.title)
              .log(location.description);

        return result;
    }
}