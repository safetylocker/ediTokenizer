package com.securitybox.storage;

public interface DataStoreDao {
    //Method to store token and vallue in the cache.
    abstract boolean storeValue(String key, String Value);
    //Method to retreive value of the token stored.
    abstract String retrieveValue(int token);
    abstract  boolean storeValue(int key,CacheEntryObject cacheEntryObject);
    abstract CacheEntryObject retrieveObject(int key);

}
