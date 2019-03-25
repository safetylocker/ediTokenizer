package com.securitybox.storage;

import com.securitybox.constants.Constants;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteState;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.TransactionException;
import org.json.JSONException;

import java.util.jar.JarEntry;

import static org.apache.ignite.cache.CacheAtomicityMode.TRANSACTIONAL;

public  class DataStore implements DataStoreDao{
    public static Ignite ignite;
    //public static IgniteCache<String, String> cache;
    IgniteCache<Integer,CacheEntryObject> objectCache;
    IgniteCache<String,CacheEntryObject> objectCacheStr;

    //initiate a ignite cache with default settings to be used for storing token and values
    /**
     * Class constructor.
     */
    public DataStore(){
        //initialize the ignite cache and start instance
        if(Ignition.state() == IgniteState.STOPPED) {
            ignite = Ignition.start();
        }
        CacheConfiguration cfg = new CacheConfiguration();
        cfg.setName(Constants.IGNITE_DEFAULT_CACHE_NAME);
        cfg.setAtomicityMode(TRANSACTIONAL);
        //cache = ignite.getOrCreateCache(cfg);
        objectCache = ignite.getOrCreateCache("CacheEntryObject");
        objectCacheStr = ignite.getOrCreateCache("CacheEntryObject");
    }


    //de-tokenize a given token
    //NOTE : if token is not found in the cache, the same token value returned
    @Override
    public String retrieveValue(int token) {
        String response="";
        try {
            response = objectCache.get(Integer.valueOf(token)).getObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString();
        } catch (Exception e) {
            e.getCause();
            response = Integer.toString(token);
        }
        if(response==null)
            response= Integer.toString(token);
        return response;
    }

    //Token type String
    @Override
    public String retrieveValue(String token) {
        String response="";
        System.out.println("Key to be detokenized : " + token);
        try {
            response = objectCacheStr.get(token).getObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString();
            System.out.println("Reterved value from cache : " + response);
        } catch (Exception e) {
            e.getCause();
            response = token;
        }
        if(response==null)
            response= token;
        return response;
    }

    //Stote object for token type integer
    @Override
    public boolean storeValue(int key,CacheEntryObject cacheEntryObject) {
        System.out.println("current hash value storeValue() " + cacheEntryObject.hashCode());
        System.out.println("current key used to cache " + key);
        try {
            System.out.println("Value inside cache object : " + cacheEntryObject.getObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            objectCache.put(key,cacheEntryObject);
        }catch (TransactionException e){
            return false;
        }
        return true;
    }

    //Stote object for token type string
    @Override
    public boolean storeValue(String key,CacheEntryObject cacheEntryObject) {
        try {
            objectCacheStr.put(key,cacheEntryObject);
        }catch (TransactionException e){
            return false;
        }
        return true;
    }

    //get CacheEntry Object for token type integer
    @Override
    public CacheEntryObject retrieveObject(int key) {
        System.out.println("Current key to detokenize retrieveObject()" + key);
        try {
            return objectCache.get(key);
        } catch (Exception e) {
            e.getCause();
            return null;
        }

    }

    //get CacheEntry Object for token type String
    @Override
    public CacheEntryObject retrieveObject(String key) {
        System.out.println("Current key to detokenize retrieveObject()" + key);
        try {
            return objectCacheStr.get(key);
        } catch (Exception e) {
            e.getCause();
            return null;
        }

    }
}
