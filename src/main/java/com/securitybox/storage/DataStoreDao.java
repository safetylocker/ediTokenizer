package com.securitybox.storage;

import com.securitybox.models.AccessEntry;
import com.securitybox.models.CacheEntryObject;

import java.util.ArrayList;

public interface DataStoreDao {
    //Store jsonObject for token type string
    boolean storeValue(String key, CacheEntryObject cacheEntryObject, String clientId);
    //Store jsonObject for token type string
    abstract boolean removeToken(String key);
    abstract CacheEntryObject retrieveObject(String key,String cleintId);
    //method to get access logs from a cache jsonObject.
    abstract ArrayList<AccessEntry> getAccessLogs(String key, String clientId);

    ArrayList<AccessEntry> getErrorEntries(String key, String clientId);

    //method to remove token entry but leave the cache object with access logs
    abstract  boolean removeTokenEntry(String key, String clientId);
    abstract  CacheEntryObject retrieveLogs(String key, String clientId);
}
