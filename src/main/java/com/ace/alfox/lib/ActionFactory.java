package com.ace.alfox.lib;

import com.ace.alfox.game.interfaces.IAction;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ActionFactory {
    public IAction action;
    public Map<String, Object> params;

    public ActionFactory(IAction a, Map<String, Object> p) {
        this.action = a;
        this.params = p;
    }

    public static ActionFactory named(String command) {
        IAction _action = null;
        try {
            _action = Database.actions.get(command).getDeclaredConstructor().newInstance();
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
