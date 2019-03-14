package com.securitybox.storage;

import org.apache.ignite.IgniteException;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;

public class CustomLifecycleBean implements LifecycleBean {
    @Override
    public void onLifecycleEvent(LifecycleEventType lifecycleEventType) throws IgniteException {

    }
}
