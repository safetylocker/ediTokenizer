package com.securitybox.storage;

import com.securitybox.constants.Constants;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteState;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteReflectionFactory;
import org.apache.ignite.transactions.TransactionException;
import org.json.JSONException;

import javax.cache.configuration.FactoryBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        cfg.setEncryptionEnabled(true);
        cfg.setCacheMode(CacheMode.LOCAL);
        IgniteReflectionFactory<CacheStore> storeFactory =
                new IgniteReflectionFactory(CacheFileLocalStore.class);
        cfg.setCacheStoreFactory(FactoryBuilder.factoryOf(storeFactory));
        //cache = ignite.getOrCreateCache(cfg);
        objectCache = ignite.getOrCreateCache("CacheEntryObject");
        objectCacheStr = ignite.getOrCreateCache("CacheEntryObject");
    }



    //Store object for token type integer
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

    //Store object for token type string
    @Override
    public boolean storeValue(String key,CacheEntryObject cacheEntryObject) {
        try {
            objectCacheStr.put(key,cacheEntryObject);
        }catch (TransactionException e){
            return false;
        }
        return true;
    }

    //Token type interger for retreiving value
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

    //Token type String for retrieving cache object
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

    //Token type interger for retreiving value while updating the access entry
    @Override
    public CacheEntryObject retrieveObject(int key, String cleintId) {
        System.out.println("Current key to detokenize retrieveObject()" + key);
        CacheEntryObject cacheEntryObject=null;
        try {
            cacheEntryObject =  objectCache.get(key);
            cacheEntryObject.accessEntries.add(new AccessEntry(new Date(),cleintId));
            objectCache.remove(key);
            objectCache.put(key,cacheEntryObject);
            return cacheEntryObject;
        } catch (Exception e) {
            e.getCause();
            return null;
        }
    }

    //Token type string for retreiving value while updating the access entry
    @Override
    public CacheEntryObject retrieveObject(String key, String cleintId) {
        System.out.println("Current key to detokenize retrieveObject()" + key);
        CacheEntryObject cacheEntryObject=null;
        try {
            cacheEntryObject =  objectCacheStr.get(key);
            cacheEntryObject.accessEntries.add(new AccessEntry(new Date(),cleintId));
            objectCacheStr.remove(key);
            objectCacheStr.put(key,cacheEntryObject);
            return cacheEntryObject;
        } catch (Exception e) {
            e.getCause();
            return null;
        }
    }

    @Override
    public ArrayList<AccessEntry> getAccessLogs(int key) {
        System.out.println("Current key to get access logs" + key);
        try {
            return objectCache.get(key).getAccessLogs();
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }

    //method to get access logs from a cache object.
    @Override
    public ArrayList<AccessEntry> getAccessLogs(String key) {
        System.out.println("Current key to get access logs" + key);
        try {
              return objectCacheStr.get(key).getAccessLogs();
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
}
