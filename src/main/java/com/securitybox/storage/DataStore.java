package com.securitybox.storage;

import com.securitybox.colloboration.ClientColloboration;
import com.securitybox.constants.Constants;
import com.securitybox.models.AccessEntry;
import com.securitybox.models.CacheEntryObject;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteState;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.*;
import org.apache.ignite.spi.encryption.keystore.KeystoreEncryptionSpi;
import org.apache.ignite.transactions.TransactionException;
import org.apache.log4j.Logger;
import org.apache.lucene.util.SloppyMath;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import static org.apache.ignite.cache.CacheAtomicityMode.TRANSACTIONAL;

public  class DataStore implements DataStoreDao{
    public static Ignite ignite;
    IgniteCache<String, CacheEntryObject> objectCacheStr;
    Properties appProps;
    final Logger logger = Logger.getLogger(this.getClass().getName());


    //initiate a ignite cache with default settings to be used for storing token and values
    public DataStore(){
        //Init application property file
        initPropertyFile();
        //initialize the ignite cache and start instance
        if(Ignition.state() == IgniteState.STOPPED) {
            CacheConfiguration cfg = new CacheConfiguration("encrypted-cache");
            cfg.setName(Constants.IGNITE_DEFAULT_CACHE_NAME);
            cfg.setAtomicityMode(TRANSACTIONAL);
            cfg.setEncryptionEnabled(true);
            cfg.setCacheMode(CacheMode.LOCAL);

            //get ignite initial configuration
            IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
            //Set ignite data storage locations
            DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();
            dataStorageConfiguration.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);
            dataStorageConfiguration.setStoragePath(System.getProperty("user.dir")
                    + System.getProperty("file.separator") + "ignite"
                    + System.getProperty("file.separator") + "storage"
                    + System.getProperty("file.separator"));
            dataStorageConfiguration.setWalPath(System.getProperty("user.dir") + System.getProperty("file.separator") + "ignite"
                    + System.getProperty("file.separator") + "walpath"
                    + System.getProperty("file.separator"));
            dataStorageConfiguration.setWalArchivePath(System.getProperty("user.dir")
                    + System.getProperty("file.separator") + "ignite"
                    + System.getProperty("file.separator") + "walarchivepath" +
                    System.getProperty("file.separator"));
            dataStorageConfiguration.setWalMode(WALMode.BACKGROUND);

            //Set encryption settings for the cache.
            KeystoreEncryptionSpi encSpi = new KeystoreEncryptionSpi();
            encSpi.setKeyStorePath(System.getProperty("user.dir")  + System.getProperty("file.separator") + "ignite_keystore.jks");
            encSpi.setKeyStorePassword(appProps.getProperty("keystore.password").toCharArray());
            //encSpi.setKeySize(256);
            encSpi.setMasterKeyName("ignite.master.key");
            igniteConfiguration.setEncryptionSpi(encSpi);

            igniteConfiguration.setDataStorageConfiguration(dataStorageConfiguration);
            igniteConfiguration.setCacheConfiguration(cfg);
            ignite = Ignition.start(igniteConfiguration);
            ignite.active(true);
        }

        objectCacheStr = ignite.getOrCreateCache(Constants.CACHE_ENTRY_OBJECT_NAME);
    }


    //Store jsonObject for token type string
    @Override
    public boolean storeValue(String key, CacheEntryObject cacheEntryObject, String clientId) {
        try {
            cacheEntryObject.accessEntries.add(new AccessEntry(new Date(),clientId,Constants.DATA_STORE_ACTION_CREATED));
            objectCacheStr.put(key,cacheEntryObject);
        }catch (TransactionException e){
            return false;
        }
        return true;
    }

    //method for storing the value in ignite cache storage.
    public boolean storeValue(String value,String key, String senderId,ArrayList<String> receiverIds) {
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,value);
            } catch (JSONException e) {
                return false;
            }
            CacheEntryObject cacheEntryObject = new CacheEntryObject(senderId,receiverIds,jsonObject);
            cacheEntryObject.accessEntries.add(new AccessEntry(new Date(),senderId,Constants.DATA_STORE_ACTION_CREATED));
            objectCacheStr.put(key,cacheEntryObject);
        }catch (TransactionException e){
            return false;
        }
        return true;
    }

    //method for removing the token from the ignite cache storage.
    @Override
    public boolean removeToken(String key) {
        return objectCacheStr.remove(key);
    }

    //Token type string for retrieving value while updating the access entry
    @Override
    public CacheEntryObject retrieveObject(String key, String clientId) {
        logger.debug("Current key to de-tokenize and used to retrieve cache object " + key);
        CacheEntryObject cacheEntryObject;
        try {
            return updateEntry(key,clientId,Constants.DATA_STORE_ACTION_DETOKENIZED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            //Return an error cache entry object back
            CacheEntryObject tempCacheEntryObject_1 = new CacheEntryObject();
            tempCacheEntryObject_1.addErrorEntry(new AccessEntry(new Date(),clientId,Constants.ERROR_TOKEN_RETRIEVE));
            return tempCacheEntryObject_1;
        }
    }

    //method for retrieving the access/audit logs from the cache storage.
     public CacheEntryObject retrieveLogs(String key, String clientId) {
        logger.debug("Current key to de-tokenize retrieve access logs " + key);
        CacheEntryObject cacheEntryObject;
        try {
            return updateEntry(key,clientId,Constants.DATA_STORE_ACTION_ACCESED_LOGS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            //Return an error cache entry object back
            CacheEntryObject tempCacheEntryObject_1 = new CacheEntryObject();
            tempCacheEntryObject_1.addErrorEntry(new AccessEntry(new Date(),clientId,Constants.ERROR_LOGS_RETRIEVE));
            return tempCacheEntryObject_1;
        }
    }

    //method for updating access entry in given cache object.
    private CacheEntryObject updateEntry(String key,String clientId,String action){
        logger.debug("Current key used to retrive cache entry object " + key);
        CacheEntryObject cacheEntryObject =  objectCacheStr.get(key);
        ClientColloboration clientColloboration = new ClientColloboration(cacheEntryObject);
        //check if client is allowed to have action on the token
        //if yes, return the new cache entry object with new access logs
        if(clientColloboration.isAllowed(clientId)) {
            cacheEntryObject.accessEntries.add(new AccessEntry(new Date(), clientId, action));
            objectCacheStr.remove(key);
            objectCacheStr.put(key, cacheEntryObject);
            return cacheEntryObject;
        } else {
            // if not update the access logs for the cache entry and reurn null
            // such that the client requesting the operation will be denied but recorded as an audit log.
            cacheEntryObject.accessEntries.add(new AccessEntry(new Date(), clientId, Constants.DATA_STORE_ACTION_DENIED));
            objectCacheStr.remove(key);
            objectCacheStr.put(key, cacheEntryObject);

            //Return an error cache entry object back
            CacheEntryObject tempCacheEntryObject_1 = new CacheEntryObject();
            tempCacheEntryObject_1.addErrorEntry(new AccessEntry(new Date(),clientId,Constants.ERROR_LOGS_RETRIEVE));
            return tempCacheEntryObject_1;
        }
    }

    //method to get access logs from a cache jsonObject.
    @Override
    public ArrayList<AccessEntry> getAccessLogs(String key,String clientId) {
        logger.debug("Current key to get access logs " + key);
        try {
            return updateEntry(key,clientId,Constants.DATA_STORE_ACTION_ACCESED_LOGS).getAccessLogs();
        } catch (Exception e) {
            logger.error(e.getCause());
            //Return and empty array list back
            return new ArrayList<AccessEntry>();
        }
    }

    @Override
    public ArrayList<AccessEntry> getErrorEntries(String key, String clientId) {
        logger.debug("Current key to get access logs " + key);
        try {
            return updateEntry(key,clientId,Constants.DATA_STORE_ACTION_ACCESED_LOGS).getErrorEntries();
        } catch (Exception e) {
            logger.error(e.getCause());
            //Return and empty array list back
            return new ArrayList<AccessEntry>();
        }
    }

    @Override
    public boolean removeTokenEntry(String key, String clientId) {
        CacheEntryObject cacheEntryObject;
        //get the existing object from the cache.
        try {
            cacheEntryObject =  objectCacheStr.get(key);
            ClientColloboration clientColloboration = new ClientColloboration(cacheEntryObject);
            if(clientColloboration.isAllowed(clientId)) {
                cacheEntryObject.accessEntries.add(new AccessEntry(new Date(), clientId, Constants.DATA_ACTION_REMOVED_TOKEN_ENTRY_DATA));
                //remove the existing object from the cache.
                if(objectCacheStr.remove(key)){
                    //create a new object to be stored with empty value.
                    JSONObject jsonObject = cacheEntryObject.getJsonObject();
                    jsonObject.remove(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME);
                    jsonObject.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME, Constants.IGNITE_DEFAULT_VALUE_DELETED);
                    cacheEntryObject.setJsonObject(jsonObject);
                    //add a modified cache object with the same key.
                    objectCacheStr.put(key, cacheEntryObject);
                    return true;
                }else{
                    return  false;
                }
            } else{
                cacheEntryObject.accessEntries.add(new AccessEntry(new Date(), clientId, Constants.DATA_STORE_ACTION_DENIED));
                //remove the existing object from the cache.
                objectCacheStr.remove(key);
                //add a modified cache object with the same key.
                objectCacheStr.put(key, cacheEntryObject);
                return false;
            }
        } catch (Exception e) {
           logger.error(e.getMessage());
            return false;
        }
    }

    public void initPropertyFile(){
        String rootPath = System.getProperty("user.dir") + System.getProperty("file.separator");
        String appConfigPath = rootPath + "app.properties";
        appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
