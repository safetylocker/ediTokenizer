package com.securitybox.tokenizer;

import com.securitybox.storage.CacheEntryObject;
import com.securitybox.storage.DataStore;

import java.security.MessageDigest;

public interface TokenizerDao<E> {
    //Interger based key tokenization
    String deTokenize(Integer token);
    int tokenize(CacheEntryObject cacheEntryObject);
    CacheEntryObject detokenize(int key);

    //String based key tokenization
    String tokenize(CacheEntryObject cacheEntryObject,String valueToTokenize,int lenght);
    CacheEntryObject detokenize(String key);
    String deTokenize(String token);
}
