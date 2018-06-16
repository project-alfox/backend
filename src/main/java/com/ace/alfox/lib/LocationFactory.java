package com.ace.alfox.lib;

import com.ace.alfox.game.models.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iq80.leveldb.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.fusesource.leveldbjni.JniDBFactory.bytes;

@Service
public class LocationFactory {

    private final DB db;

    @Autowired
    public LocationFactory(Database leveldb) throws IOException {
        db = leveldb.get();
    }

    public Location fetch(int x, int y) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            byte[] value = db.get(bytes("location-" + x + "-" + y));
            return mapper.readValue(value, Location.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Location() {{
            coordinates = new Vector2(x, y);
        }};
    }

    public Location fetch(Vector2 location) {
        return fetch(location.x(), location.y());
    }

    public void save(Location location) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            db.put(bytes("location-" + location.coordinates.x() + "-" + location.coordinates.y()), mapper.writeValueAsBytes(location));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
