package com.securitybox.storage;

import com.securitybox.constants.Constants;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteState;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.configuration.*;
import org.apache.ignite.transactions.TransactionException;
import org.apache.maven.shared.utils.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.cache.configuration.FactoryBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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
            CacheConfiguration cfg = new CacheConfiguration();
            cfg.setName(Constants.IGNITE_DEFAULT_CACHE_NAME);
            cfg.setAtomicityMode(TRANSACTIONAL);
            //cfg.setEncryptionEnabled(true);
            cfg.setCacheMode(CacheMode.REPLICATED);

            IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
            DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();
            dataStorageConfiguration.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);
            dataStorageConfiguration.setWalPath(System.getProperty("user.dir") + "\\ignite\\walpath\\");
            dataStorageConfiguration.setWalArchivePath(System.getProperty("user.dir") + "\\ignite\\walarchivepath\\");
            igniteConfiguration.setDataStorageConfiguration(dataStorageConfiguration);
            igniteConfiguration.setCacheConfiguration(cfg);
            String cwd = System.getProperty("user.dir");

            ignite = Ignition.start(igniteConfiguration);
            ignite.active(true);
            System.out.println("PATH " + cwd);
        }
        //cache = ignite.getOrCreateCache(cfg);
        objectCache = ignite.getOrCreateCache(Constants.CACHE_ENTRY_OBJECT_NAME);
        objectCacheStr = ignite.getOrCreateCache(Constants.CACHE_ENTRY_OBJECT_NAME);
    }



    //Store jsonObject for token type integer
    @Override
    public boolean storeValue(int key,CacheEntryObject cacheEntryObject) {
        System.out.println("current hash value storeValue() " + key);
        try {
            System.out.println("Value inside cache jsonObject : " + cacheEntryObject.getJsonObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());
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

    //Store jsonObject for token type string
    @Override
    public boolean storeValue(String key,CacheEntryObject cacheEntryObject) {
        try {
            objectCacheStr.put(key,cacheEntryObject);
        }catch (TransactionException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean removeToken(String key) {
        if (StringUtils.isNumeric(key)) {
            return objectCache.remove(Integer.valueOf(key));
        } else {
            return objectCacheStr.remove(key);
        }
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

    //Token type String for retrieving cache jsonObject
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
        CacheEntryObject cacheEntryObject;
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
        CacheEntryObject cacheEntryObject;
        try {
            cacheEntryObject =  objectCacheStr.get(key);
            cacheEntryObject.accessEntries.add(new AccessEntry(new Date(),cleintId));
            objectCacheStr.remove(key);
            objectCacheStr.put(key,cacheEntryObject);
            return cacheEntryObject;
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    //method to get access logs from a cache jsonObject.
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

    @Override
    public boolean removeTokenEntry(String key, String clientId) {
        CacheEntryObject cacheEntryObject;
        //get the existing object from the cache.
        try {
            if (StringUtils.isNumeric(key)) {
                cacheEntryObject =  objectCache.get(Integer.valueOf(key));
            }else{
                cacheEntryObject =  objectCacheStr.get(key);
            }

            cacheEntryObject.accessEntries.add(new AccessEntry(new Date(),clientId));

            //remove the existing object from the cache.
            if (StringUtils.isNumeric(key)) {
                objectCache.remove(Integer.valueOf(key));
            }else{
                objectCacheStr.remove(key);
            }

            //create a new oject to be stored with empty value
            JSONObject jsonObject = cacheEntryObject.getJsonObject();
            jsonObject.remove(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME);
            jsonObject.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,Constants.IGNITE_DEFAULT_VALUE_DELETED);
            cacheEntryObject.setJsonObject(jsonObject);

            //add a modofied cache object with the same key
            if (StringUtils.isNumeric(key)) {
                objectCache.put(Integer.valueOf(key),cacheEntryObject);
            }else {
                objectCacheStr.put(key,cacheEntryObject);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


}
