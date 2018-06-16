package com.ace.alfox;

import com.ace.alfox.game.MoveAction;
import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.LocationFactory;
import com.ace.alfox.lib.Vector2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoveActionTests {
    Player player;
    IAction move;

    @Autowired
    LocationFactory lf;

    @Before
    public void setUp() {
        player = new Player();
        move = new MoveAction(lf);
    }

    @Test
    public void Move() {
        ActionResult result = move.applyAction(player,
                new HashMap<String, Object>() {{
                    put("direction", "north");
                }} );

        assertArrayEquals(result.player.location.toArray(), new Vector2(0,1).toArray());
    }

    @Test
    public void InvalidMove() {
        ActionResult result = move.applyAction(player,
                new HashMap<String, Object>() {{
                    put("direction", "westward");
                }} );

        assertFalse(result.ok);
        assertArrayEquals(result.player.location.toArray(), new Vector2(0,0).toArray());
    }
}