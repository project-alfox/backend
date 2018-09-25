package com.ace.alfox.game.models;

import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.Vector2;
import java.util.Map;
import org.dizitart.no2.objects.Id;

public class Player {

  @Id public Long id;
  public String name = "Jimmy Fred";
  public int hp = 100;
  public Vector2 location = new Vector2(0, 0);
  public PlayerState playerState = PlayerState.FREE;
  public Long activeBattleId = null;

  public ActionResult applyAction(IAction doSomething, Map<String, Object> params) {
    return doSomething.applyAction(this, params);
  }

  public boolean canMove() {
    return PlayerState.FREE.equals(playerState);
  }

  public boolean inBattle() {
    return PlayerState.IN_BATTLE.equals(playerState);
  }

  public enum PlayerState {
    FREE,
    IN_BATTLE;
  }
}
