package com.securitybox.storage;

import com.securitybox.constants.Constants;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.TransactionException;
import static org.apache.ignite.cache.CacheAtomicityMode.TRANSACTIONAL;

public  class DataStore implements DataStoreDao{
    public static Ignite ignite;
    public static IgniteCache<String, String> cache;

    //initiate a ignite cache with default settings to be used for storing token and values
    public DataStore(){
        //initialize the ignite cache and start instance
        ignite = Ignition.start();
        CacheConfiguration cfg = new CacheConfiguration();
        cfg.setName(Constants.IGNITE_DEFAULT_CACHE_NAME);
        cfg.setAtomicityMode(TRANSACTIONAL);
        cache = ignite.getOrCreateCache(cfg);
    }

    //function to store the key value pair, given a token together with the string value
    @Override
    public  boolean storeValue(String key, String value) {
        try {
            cache.put(key, value);
        }catch (TransactionException e){
            return false;
        }
        return true;
    }

    //de-tokenize a given token
    //NOTE : if token is not found in the cache, the same token value returned
    @Override
    public String retrieveValue(String token) {
        String response="";
        try {
            response = cache.get(token);
            if(response==null)
                response=token;
        }catch(TransactionException e){
            System.out.println(e.getCause());
            response = token;
        }
        return response;
    }
}
