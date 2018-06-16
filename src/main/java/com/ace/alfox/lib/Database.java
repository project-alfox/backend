package com.ace.alfox.lib;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static org.fusesource.leveldbjni.JniDBFactory.factory;

@Service
public class Database {
    private static DB instance;

    public Database() {}

    public DB get() throws IOException {
        return get("Game");
    }

    public synchronized DB get(String name) throws IOException {
        if(Database.instance != null)
            return Database.instance;

        Options options = new Options();
        options.createIfMissing(true);
        DB db = factory.open(new File(name), options);
        Database.instance = db;

        return db;
    }

    //TODO add this to global teardown hook
    public void destroy() throws IOException {
        if(Database.instance != null)
            Database.instance.close();
    }
}
