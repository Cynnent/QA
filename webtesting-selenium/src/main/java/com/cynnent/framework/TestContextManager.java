package com.cynnent.framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;

public class TestContextManager {
    private static ThreadLocal<ITestContext> testContext = new ThreadLocal<>();
    private static final Logger log = LogManager.getLogger(TestContextManager.class);

    public static void setTestContext(ITestContext context) {
        testContext.set(context);
        log.info("TestContext set for thread: {}", Thread.currentThread().getId());
    }

    public static ITestContext getTestContext() {
        return testContext.get();
    }
    
    public static void removeTestContext() {
        testContext.remove();
        log.info("TestContext removed for thread: {}", Thread.currentThread().getId());
    }
    
    public static void setRuntimeValue(String key, Object value) {
        ITestContext context = getTestContext();
        if (context != null) {
            context.setAttribute(key, value);
            log.info("Runtime value set: [Key: {}, Value: {}]", key, value);
        } else {
            log.warn("Cannot set runtime value. Test context is not available.");
        }
    }

    public static Object getRuntimeValue(String key) {
        ITestContext context = getTestContext();
        if (context != null) {
            Object value = context.getAttribute(key);
            log.info("Runtime value retrieved: [Key: {}, Value: {}]", key, value);
            return value;
        } else {
            log.warn("Cannot get runtime value. Test context is not available.");
            return null;
        }
    }
}
