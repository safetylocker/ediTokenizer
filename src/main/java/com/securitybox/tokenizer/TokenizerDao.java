package com.securitybox.tokenizer;

import com.securitybox.storage.CacheEntryObject;
import com.securitybox.storage.DataStore;

import java.security.MessageDigest;

public interface TokenizerDao<E> {
    String deTokenize(Integer token);
    int tokenize(CacheEntryObject cacheEntryObject);
    CacheEntryObject detokenize(int key);

    String tokenize(CacheEntryObject cacheEntryObject,String valueToTokenize,int lenght);
    CacheEntryObject detokenize(String key);
    String deTokenize(String token);
}
