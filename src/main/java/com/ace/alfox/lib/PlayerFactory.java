package com.ace.alfox.lib;

import com.ace.alfox.game.models.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iq80.leveldb.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.fusesource.leveldbjni.JniDBFactory.bytes;

@Service
public class PlayerFactory {

    private final DB db;

    @Autowired
    public PlayerFactory(Database leveldb) throws IOException {
        db = leveldb.get();
    }

    public Player fetch(String id) {
        if(id == null) { // for testing
            id = "1234-1234-1234-1234";
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            byte[] value = db.get(bytes("player-" + id));
            return mapper.readValue(value, Player.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // for testing
        return new Player();
    }

    public void save(Player player) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            db.put(bytes("player-" + player.id), mapper.writeValueAsBytes(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
