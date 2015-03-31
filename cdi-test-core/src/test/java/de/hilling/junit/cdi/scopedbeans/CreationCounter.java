package de.hilling.junit.cdi.scopedbeans;

import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.apache.deltaspike.core.util.ProxyUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@TestSuiteScoped
public class CreationCounter implements Serializable {

    private Map<Class<?>, Integer> creations;
    private Map<Class<?>, Integer> deletions;

    @PostConstruct
    public void reset() {
        creations = new HashMap<>();
        deletions = new HashMap<>();
    }

    public int getCreations(Class<?> callerClass) {
        return valueOrZero(this.creations.get(callerClass));
    }

    public int getDeletions(Class<?> callerClass) {
        return valueOrZero(deletions.get(callerClass));
    }

    private int valueOrZero(Integer value) {
        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }

    public void created(Object caller) {
        Class<?>callerClass= getCaller(caller);
        int current = getCreations(callerClass);
        creations.put(callerClass, current + 1);
    }

    public void deleted(Object caller) {
        Class<?>callerClass= getCaller(caller);
        int current = getDeletions(callerClass);
        deletions.put(callerClass, current + 1);
    }

    private Class<?> getCaller(Object caller) {
        return ProxyUtils.getUnproxiedClass(caller.getClass());
    }
}
