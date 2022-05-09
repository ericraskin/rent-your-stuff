package com.paslists.rys.test_support;

import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_TestDataCleanup")
public class DatabaseCleanup<T> {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    public void removeAllEntities(Class<T> entityClass) {
        SaveContext removeContext = new SaveContext();
        systemAuthenticator.withSystem(() -> {
            dataManager.load(entityClass).all().list().forEach(removeContext::removing);
            dataManager.save(removeContext);
            return null;
        });
    }
}