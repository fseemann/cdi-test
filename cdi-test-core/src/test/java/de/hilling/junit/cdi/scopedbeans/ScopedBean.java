package de.hilling.junit.cdi.scopedbeans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.UUID;

public class ScopedBean {

    @Inject
    private Instance<CreationCounter> creationCounter;

    @PostConstruct
    public void postConstruct() {
        creationCounter.get().created(this);
    }

    @PreDestroy
    public void preDestroy() {
        creationCounter.get().deleted(this);
    }

    protected UUID uuid;

    public ScopedBean() {
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

}