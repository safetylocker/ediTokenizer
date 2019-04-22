package com.securitybox.storage;

import com.securitybox.models.AccessEntry;
import com.securitybox.models.CacheEntryObject;

import java.util.ArrayList;

public interface DataStoreDao {
    //store cache entry object in token storage
    boolean storeValue(String key, CacheEntryObject cacheEntryObject, String clientId);
    //remove cache object related to provided token from token storage
    abstract boolean removeToken(String key);
    //retrieves the cache entry object for given token
    abstract CacheEntryObject retrieveObject(String key,String cleintId);
    //retrieves the access/audit logs from a cache entry object for a given token.
    abstract ArrayList<AccessEntry> getAccessLogs(String key, String clientId);
    //retrieves error entries from a cache entry object for a given token.
    ArrayList<AccessEntry> getErrorEntries(String key, String clientId);
    //method to remove token entry but leave the cache object with access logs
    abstract  boolean removeTokenEntry(String key, String clientId);
    //retrieves access/audit logs from a cache entry object related to token.
    abstract  CacheEntryObject retrieveLogs(String key, String clientId);
}
