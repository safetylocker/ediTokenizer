package com.securitybox.tokenizer;

import com.securitybox.storage.CacheEntryObject;
import com.securitybox.storage.DataStore;

import java.security.MessageDigest;

public interface TokenizerDao<E> {
    int tokenize(CacheEntryObject cacheEntryObject);
    String tokenize(CacheEntryObject cacheEntryObject,String valueToTokenize,int lenght);
    CacheEntryObject deTokenize(String key);

}
