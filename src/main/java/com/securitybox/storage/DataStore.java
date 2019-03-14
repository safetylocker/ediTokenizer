package com.securitybox.storage;

import com.securitybox.constants.Constants;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.TransactionException;

import javax.transaction.TransactionRequiredException;

import static java.lang.Enum.valueOf;
import static org.apache.ignite.cache.CacheAtomicityMode.TRANSACTIONAL;

public  class DataStore implements DataStoreDao{
    public static Ignite ignite;
    public static IgniteCache<String, String> cache;
    public DataStore(){
        ignite = Ignition.start();
        CacheConfiguration cfg = new CacheConfiguration();
        cfg.setName(Constants.IGNITE_DEFAULT_CACHE_NAME);
        cfg.setAtomicityMode(TRANSACTIONAL);
        cache = ignite.getOrCreateCache(cfg);


    }

    @Override
    public  boolean storeValue(String key, String value) {
        try {
            cache.put(key, value);
        }catch (TransactionException e){
            return false;
        }
        return true;
    }

    @Override
    public String retrieveValue(String token) {
        try {
            return cache.get(token);
        }catch(TransactionException e){
            System.out.println(e.getCause());
            return null;
        }
    }
}
