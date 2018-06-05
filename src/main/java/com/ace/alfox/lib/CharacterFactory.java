package com.ace.alfox.lib;

import com.ace.alfox.game.GameCharacter;

public class CharacterFactory {
    public static GameCharacter _testSubject = new GameCharacter();

    public static GameCharacter fetch(String id) {
        // TODO: make copy of character from database to prevent multi-thread access
        return _testSubject;
    }

    public static void save(GameCharacter character) {
        // TODO: real implementation
        _testSubject = character;
    }
}
