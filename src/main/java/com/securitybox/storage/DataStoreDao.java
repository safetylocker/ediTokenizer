package com.securitybox.storage;

import java.util.ArrayList;

public interface DataStoreDao {
    abstract  boolean storeValue(int key,CacheEntryObject cacheEntryObject);
    abstract CacheEntryObject retrieveObject(int key);
    abstract CacheEntryObject retrieveObject(int key,String clientId);

    abstract boolean storeValue(String key,CacheEntryObject cacheEntryObject);
    CacheEntryObject retrieveObject(String key);
    CacheEntryObject retrieveObject(String key,String cleintId);

    //method to get access logs from a cache object.
    abstract ArrayList<AccessEntry> getAccessLogs(int key);
    abstract ArrayList<AccessEntry> getAccessLogs(String key);
}
