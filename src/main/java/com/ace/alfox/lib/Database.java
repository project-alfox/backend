package com.ace.alfox.lib;

import com.ace.alfox.game.models.Location;
import com.ace.alfox.game.models.Player;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteBuilder;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Service
public class Database {
    final Nitrite db;

    public ObjectRepository<Player> players;
    public ObjectRepository<Location> locations;

    public Database(@Value(value = "game") String name) {
        NitriteBuilder _db = Nitrite.builder();
        if(!name.equals("memory")) {
            _db = _db.filePath(name + ".db");
        }
        db = _db.openOrCreate();
        players = db.getRepository(Player.class);
        locations = db.getRepository(Location.class);
    }

    @PreDestroy
    public void destroy() {
        db.close();
    }
}
