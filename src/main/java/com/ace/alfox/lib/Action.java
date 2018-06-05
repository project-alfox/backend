package com.ace.alfox.lib;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Action {
    public IAction action;
    public Map<String, Object> params;

    public Action(IAction a, Map<String, Object> p) {
        this.action = a;
        this.params = p;
    }

    public static Action named(String command) {
        IAction _action = null;
        try {
            _action = ActionDatabase.actions.get(command).getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new Action(_action, null);
    }

    public Action withParameters(Map<String, Object> p) {
        return new Action(this.action, p);
    }
}
