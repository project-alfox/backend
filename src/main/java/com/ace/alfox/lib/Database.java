package com.ace.alfox.lib;

import com.ace.alfox.game.interfaces.IAction;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import static org.fusesource.leveldbjni.JniDBFactory.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Database {
    /**
     * Mappings of /perform/{action} to an IAction. Populated in the AlfoxApplication class.
     */
    public static Map<String, Class<IAction>> actions = new HashMap<>();
    private static DB instance;

    private Database() {}

    public static void findActions(String namespace) throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AnnotationTypeFilter(PlayerAction.class));
        for (BeanDefinition bd : scanner.findCandidateComponents(namespace)) {
            // use my annotation to get simple name
            String alias = Class.forName(bd.getBeanClassName()).getAnnotation(PlayerAction.class).alias();
            System.out.println(alias);
            Class cl = Class.forName(bd.getBeanClassName());
            Database.actions.put(alias, cl);
        }
    }

    public synchronized static DB get() throws IOException {
        if(Database.instance != null)
            return Database.instance;

        Options options = new Options();
        options.createIfMissing(true);
        DB db = factory.open(new File("Game"), options);
        Database.instance = db;

        return db;
    }

    //TODO add this to global teardown hook
    public void destroy() throws IOException {
        if(Database.instance != null)
            Database.instance.close();
    }
}
