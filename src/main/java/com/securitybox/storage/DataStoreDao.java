package com.securitybox.storage;

public interface DataStoreDao {
    abstract  boolean storeValue(int key,CacheEntryObject cacheEntryObject);
    abstract CacheEntryObject retrieveObject(int key);
    abstract boolean storeValue(String key,CacheEntryObject cacheEntryObject);
    CacheEntryObject retrieveObject(String key);
    String retrieveValue(String token);

}
