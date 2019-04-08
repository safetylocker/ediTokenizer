package com.securitybox.storage;

import java.util.ArrayList;

public interface DataStoreDao {
    abstract  boolean storeValue(int key,CacheEntryObject cacheEntryObject);
    abstract CacheEntryObject retrieveObject(int key);
    abstract CacheEntryObject retrieveObject(int key,String clientId);

    abstract boolean storeValue(String key,CacheEntryObject cacheEntryObject);

    abstract boolean removeToken(String key);

    @Deprecated
    abstract CacheEntryObject retrieveObject(String key);
    abstract CacheEntryObject retrieveObject(String key,String cleintId);

    //method to get access logs from a cache jsonObject.
    abstract ArrayList<AccessEntry> getAccessLogs(int key);
    abstract ArrayList<AccessEntry> getAccessLogs(String key);
}
