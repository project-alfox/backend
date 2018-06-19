package com.ace.alfox.lib;

import com.ace.alfox.game.interfaces.IAction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ActionFactory {
    public IAction action;
    public Map<String, Object> params;
    /**
     * Mappings of /perform/{action} to an IAction. Populated in the AlfoxApplication class.
     */
    private Map<String, Class<IAction>> actions = new HashMap<>();

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    public ActionFactory(@Value("com.ace.alfox.game") String namespace) {
        if(namespace == null) { return; }

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AnnotationTypeFilter(PlayerAction.class));
        for (BeanDefinition bd : scanner.findCandidateComponents(namespace)) {
            // use the PlayerAction annotation to get the aliased endpoint name
            String alias = null;
            try {
                alias = Class.forName(bd.getBeanClassName()).getAnnotation(PlayerAction.class).alias();
                Class cl = Class.forName(bd.getBeanClassName());
                actions.put(alias, cl);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public ActionFactory(IAction a, Map<String, Object> p) {
        this.action = a;
        this.params = p;
    }

    public void setActions(HashMap<String, Class<IAction>> actions) {
        this.actions = actions;
    }

    public ActionFactory named(String command) {
        IAction _action = null;
        try {
            _action = beanFactory.createBean(actions.get(command));
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return new ActionFactory(_action, null);
    }

    public ActionFactory withParameters(Map<String, Object> p) {
        return new ActionFactory(this.action, p);
    }
}
