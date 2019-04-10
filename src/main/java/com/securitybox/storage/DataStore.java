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
    IgniteCache<String,CacheEntryObject> objectCacheStr;

    //initiate a ignite cache with default settings to be used for storing token and values
    public DataStore(){
        //initialize the ignite cache and start instance
        if(Ignition.state() == IgniteState.STOPPED) {
            CacheConfiguration cfg = new CacheConfiguration();
            cfg.setName(Constants.IGNITE_DEFAULT_CACHE_NAME);
            cfg.setAtomicityMode(TRANSACTIONAL);
            //cfg.setEncryptionEnabled(true);
            cfg.setCacheMode(CacheMode.LOCAL);

            IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
            DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();
            //dataStorageConfiguration.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);
            dataStorageConfiguration.setWalPath(System.getProperty("user.dir") + "\\ignite\\walpath\\");
            dataStorageConfiguration.setWalArchivePath(System.getProperty("user.dir") + "\\ignite\\walarchivepath\\");
            dataStorageConfiguration.setWalMode(WALMode.BACKGROUND);
            igniteConfiguration.setDataStorageConfiguration(dataStorageConfiguration);
            igniteConfiguration.setCacheConfiguration(cfg);
            ignite = Ignition.start(igniteConfiguration);
            //ignite.active(true);

        }
        //cache = ignite.getOrCreateCache(cfg);
        //objectCache = ignite.getOrCreateCache(Constants.CACHE_ENTRY_OBJECT_NAME);
        objectCacheStr = ignite.getOrCreateCache(Constants.CACHE_ENTRY_OBJECT_NAME);
    }


    //Store jsonObject for token type string
    @Override
    public boolean storeValue(String key,CacheEntryObject cacheEntryObject,String clientId) {
        try {
            cacheEntryObject.accessEntries.add(new AccessEntry(new Date(),clientId,Constants.DATA_STORE_ACTION_CREATED));
            objectCacheStr.put(key,cacheEntryObject);
        }catch (TransactionException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean removeToken(String key) {

        return objectCacheStr.remove(key);
    }

     //Token type string for retreiving value while updating the access entry
    @Override
    public CacheEntryObject retrieveObject(String key, String clientId) {
        System.out.println("Current key to detokenize retrieveObject()" + key);
        CacheEntryObject cacheEntryObject;
        try {
            return updateEntry(key,clientId,Constants.DATA_STORE_ACTION_DETOKENIZED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private CacheEntryObject updateEntry(String key,String clientId,String action){
        CacheEntryObject cacheEntryObject =  objectCacheStr.get(key);
        cacheEntryObject.accessEntries.add(new AccessEntry(new Date(),clientId,action));
        objectCacheStr.remove(key);
        objectCacheStr.put(key,cacheEntryObject);
        return cacheEntryObject;
    }

    //method to get access logs from a cache jsonObject.
    @Override
    public ArrayList<AccessEntry> getAccessLogs(String key,String clientId) {
        System.out.println("Current key to get access logs" + key);
        try {
            return updateEntry(key,clientId,Constants.DATA_STORE_ACTION_ACCESED_LOGS).getAccessLogs();
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
            cacheEntryObject =  objectCacheStr.get(key);
            cacheEntryObject.accessEntries.add(new AccessEntry(new Date(),clientId,Constants.DATA_ACTION_REMOVED_TOKEN_ENTRY_DATA));
            //remove the existing object from the cache.
            objectCacheStr.remove(key);
            //create a new oject to be stored with empty value
            JSONObject jsonObject = cacheEntryObject.getJsonObject();
            jsonObject.remove(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME);
            jsonObject.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,Constants.IGNITE_DEFAULT_VALUE_DELETED);
            cacheEntryObject.setJsonObject(jsonObject);
            //add a modofied cache object with the same key
            objectCacheStr.put(key,cacheEntryObject);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


}
