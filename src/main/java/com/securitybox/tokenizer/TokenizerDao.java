package com.securitybox.tokenizer;

import com.securitybox.storage.CacheEntryObject;
import com.securitybox.storage.DataStore;

import java.security.MessageDigest;

public interface TokenizerDao<E> {

    //tokenize a cache object
    //abstract int tokenize(CacheEntryObject cacheEntryObject);
    //String tokenize(CacheEntryObject cacheEntryObject,String valueToTokenize,int lenght,String clientId);
    //Method for detokenizing a given token
    abstract CacheEntryObject deTokenize(String key,String clientId);

    //tokenize based on input value not object type
    String tokenize(CacheEntryObject cacheEntryObject, String valueToTokenize, int lenght, String clientId);

    //Function to remove the cotent from the cache entry from the data store.
    //Cache object should not be removed, such that access logs still be availalbe for future reference for a given token
    abstract boolean removeTokenEntry(String key, String clientId);

    abstract boolean removeToken(String key);
}
