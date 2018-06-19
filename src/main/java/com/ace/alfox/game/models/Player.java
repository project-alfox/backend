package com.ace.alfox.game.models;

import com.ace.alfox.lib.ActionFactory;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.Vector2;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

public class Player {
    @Id
    public NitriteId id;
    public String name = "Jimmy Fred";
    public int hp = 100;
    public Vector2 location = new Vector2(0,0);

    public ActionResult applyAction(ActionFactory doSomething) {
        // TODO: clone player
        return doSomething.action.applyAction(this, doSomething.params);
    }
}
