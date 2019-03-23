package com.securitybox.tokenizer;

import com.securitybox.storage.CacheEntryObject;
import com.securitybox.storage.DataStore;

import java.security.MessageDigest;

public interface TokenizerDao<E> {
    String tokenize(String input, int keyLenght);
    String deTokenize(Integer token);
    int tokenize(CacheEntryObject cacheEntryObject);
    CacheEntryObject detokenize(int key);
}
