package com.ace.alfox.lib;

import com.ace.alfox.game.interfaces.IAction;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    public IAction action;
    public Map<String, Object> params;
    /**
     * Mappings of /perform/{action} to an IAction. Populated in the AlfoxApplication class.
     */
    private static Map<String, Class<IAction>> actions = new HashMap<>();

    public ActionFactory(IAction a, Map<String, Object> p) {
        this.action = a;
        this.params = p;
    }

    public static void findActions(String namespace) throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AnnotationTypeFilter(PlayerAction.class));
        for (BeanDefinition bd : scanner.findCandidateComponents(namespace)) {
            // use my annotation to get simple name
            String alias = Class.forName(bd.getBeanClassName()).getAnnotation(PlayerAction.class).alias();
            System.out.println(alias);
            Class cl = Class.forName(bd.getBeanClassName());
            ActionFactory.actions.put(alias, cl);
        }
    }

    public static void populateActions(HashMap<String, Class<IAction>> actions) {
        ActionFactory.actions = actions;
    }

    public static ActionFactory named(String command) {
        IAction _action = null;
        try {
            _action = ActionFactory.actions.get(command).getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new ActionFactory(_action, null);
    }

    public ActionFactory withParameters(Map<String, Object> p) {
        return new ActionFactory(this.action, p);
    }
}
