package com.ace.alfox.game.interfaces;

import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionWebResult;

import java.util.Map;

public interface IAction {
    ActionWebResult applyAction(Player player, Map<String, Object> params);
}
