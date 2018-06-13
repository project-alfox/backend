package com.ace.alfox.lib;

import com.ace.alfox.game.models.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iq80.leveldb.DB;

import static org.fusesource.leveldbjni.JniDBFactory.bytes;

public class CharacterFactory {
    public static Player fetch(String id) {
        if(id == null) { // for testing
            id = "1234-1234-1234-1234";
        }
        try {
            DB db = Database.get();
            ObjectMapper mapper = new ObjectMapper();
            byte[] value = db.get(bytes("character-" + id));
            return mapper.readValue(value, Player.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // for testing
        return new Player();
    }

    public static void save(Player character) {
        try {
            DB db = Database.get();
            ObjectMapper mapper = new ObjectMapper();
            db.put(bytes("character-" + character.id), mapper.writeValueAsBytes(character));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
