package com.ace.alfox.lib;

import com.ace.alfox.game.GameCharacter;

import java.util.Map;

public interface IAction {
    Result applyAction(GameCharacter player, Map<String, Object> params);
}
