package com.securitybox.storage;

public interface DataStoreDao {
    abstract boolean storeValue(String key, String Value);
    String retrieveValue(String token);
}
