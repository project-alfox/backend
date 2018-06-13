package com.ace.alfox.lib;

import com.ace.alfox.game.models.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iq80.leveldb.DB;

import static org.fusesource.leveldbjni.JniDBFactory.bytes;

public class LocationFactory {
    public Location fetch(int x, int y) {
        try {
            DB db = Database.get();
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

    public void save(Location location) {
        try {
            DB db = Database.get();
            ObjectMapper mapper = new ObjectMapper();
            db.put(bytes("location-" + location.coordinates.x() + "-" + location.coordinates.y()), mapper.writeValueAsBytes(location));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
