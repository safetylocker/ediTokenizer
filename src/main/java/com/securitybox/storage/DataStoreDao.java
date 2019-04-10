package com.securitybox.storage;

import java.util.ArrayList;

public interface DataStoreDao {
    abstract boolean storeValue(String key,CacheEntryObject cacheEntryObject);
    abstract boolean removeToken(String key);
    abstract CacheEntryObject retrieveObject(String key,String cleintId);
    //method to get access logs from a cache jsonObject.
    abstract ArrayList<AccessEntry> getAccessLogs(String key,String clientId);
    //method to remove token entry but leave the cache object with access logs
    abstract  boolean removeTokenEntry(String key, String clientId);
}
