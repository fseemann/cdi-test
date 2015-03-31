package de.hilling.junit.cdi;

import de.hilling.junit.cdi.scopedbeans.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Test if only one test class.
 *
 * @author gunnar
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCaseScopeTest extends CdiTestAbstract {

    private static Map<Class<?>, UUID> firstUuids = new HashMap<>();

    @Inject
    private TestSuiteScopedBean testSuiteScopedBean;
    @Inject
    private TestScopedBean testScopedBean;
    @Inject
    private ApplicationScopedBean applicationScopedBean;
    @Inject
    private RequestScopedBean requestScopedBean;
    @Inject
    private SessionScopedBean sessionScopedBean;
    @Inject
    private CreationCounter creationCounter;

    @Before
    public void setUp() {
        applicationScopedBean.getDependentScopedBean();
    }

    @Test
    public void test1() {
        assertInstances();
        assertCreationsDeletions(1, 0, ApplicationScopedBean.class);
    }

    private void assertCreationsDeletions(int creations, int deletions, Class<?> beanClass) {
        Assert.assertEquals("creations", creations, creationCounter.getCreations(beanClass));
        Assert.assertEquals("deletions", deletions, creationCounter.getDeletions(beanClass));
    }

    @Test
    public void test2() {
        assertInstances();
        assertCreationsDeletions(2, 1, ApplicationScopedBean.class);
    }

    @Test
    public void test3() {
        assertInstances();
        assertCreationsDeletions(3, 2, ApplicationScopedBean.class);
    }

    @Test
    public void testDependents() {
        DependentScopedBean dependent1 = requestScopedBean.getDependentScopedBean();
        DependentScopedBean dependent2 = applicationScopedBean.getDependentScopedBean();
        Assert.assertNotSame(dependent1, dependent2);
    }

    private void assertInstances() {
        assertInstanceNotSame(testScopedBean);
        assertInstanceNotSame(applicationScopedBean);
        assertInstanceNotSame(requestScopedBean);
        assertInstanceNotSame(sessionScopedBean);
        assertInstanceSame(testSuiteScopedBean);
    }

    private void assertInstanceNotSame(ScopedBean bean) {
        Class<? extends ScopedBean> beanKey = bean.getClass();
        if (firstUuids.containsKey(beanKey)) {
            Assert.assertNotSame(firstUuids.get(beanKey), bean.getUuid());
        } else {
            firstUuids.put(beanKey, bean.getUuid());
        }
    }

    private void assertInstanceSame(ScopedBean bean) {
        Class<? extends ScopedBean> beanKey = bean.getClass();
        if (firstUuids.containsKey(beanKey)) {
            Assert.assertSame(firstUuids.get(beanKey), bean.getUuid());
        } else {
            firstUuids.put(beanKey, bean.getUuid());
        }
    }
}
