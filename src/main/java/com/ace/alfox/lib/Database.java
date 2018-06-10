package com.ace.alfox.lib;

import com.ace.alfox.game.GameCharacter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import static org.fusesource.leveldbjni.JniDBFactory.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.fusesource.leveldbjni.JniDBFactory.bytes;

public class Database {
    private static HashMap<Keys, DB> instance = new HashMap<>();

    private Database() {}

    public synchronized static DB get(Keys name) throws IOException {
        if(Database.instance.containsKey(name))
            return Database.instance.get(name);

        Options options = new Options();
        options.createIfMissing(true);
        DB db = factory.open(new File(name.toString()), options);
        Database.instance.put(name, db);

        return db;
    }

    //TODO add this to global teardown hook
    public void destroy() {
        instance.forEach((key, val) -> {
            try {
                val.close();
            } catch (IOException e) {}
        });
    }

    public static enum Keys {
        Session, Character
    }
}
