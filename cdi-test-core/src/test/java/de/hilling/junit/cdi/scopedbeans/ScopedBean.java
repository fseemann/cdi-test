package de.hilling.junit.cdi.scopedbeans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

public class ScopedBean {

    public static int getCreations() {
        return creations;
    }

    private static int creations;

    public static int getDeletions() {
        return deletions;
    }

    private static int deletions;

    @PostConstruct
    public void postConstruct() {
        creations++;
    }

    @PreDestroy
    public void preDestroy() {
        deletions++;
    }

    protected UUID uuid;

    public ScopedBean() {
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public static void resetCounters() {
        deletions = 0;
        creations = 0;
    }
}