package com.securitybox.tokenizer;

import com.securitybox.models.CacheEntryObject;

import java.util.ArrayList;

public interface TokenizerDao {

    //Method to tokenize a given value
    public String tokenize(String valueToTokenize, int length, String senderId, ArrayList<String> receiverIds);

    //Method for de-tokenize a given token
    abstract CacheEntryObject deTokenize(String key,String clientId);

    //tokenize based on input value not object type
    @Deprecated
    String tokenize(CacheEntryObject cacheEntryObject, String valueToTokenize, int lenght, String clientId);

    //Function to remove the content from the cache entry from the data store.
    //Cache object should not be removed, such that access logs still be available for future reference for a given token.
    abstract boolean removeTokenEntry(String key, String clientId);
    //method to remove a token and its entries from the storage.
    abstract boolean removeToken(String key);
}
