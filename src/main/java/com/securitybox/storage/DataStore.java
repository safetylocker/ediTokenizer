package com.securitybox.storage;

import com.securitybox.constants.Constants;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.TransactionException;
import org.json.JSONException;

import static org.apache.ignite.cache.CacheAtomicityMode.TRANSACTIONAL;

public  class DataStore implements DataStoreDao{
    public static Ignite ignite;
    public static IgniteCache<String, String> cache;
    IgniteCache<Integer,CacheEntryObject> objectCache;

    //initiate a ignite cache with default settings to be used for storing token and values
    /**
     * Class constructor.
     */
    public DataStore(){
        //initialize the ignite cache and start instance
        ignite = Ignition.start();
        CacheConfiguration cfg = new CacheConfiguration();
        cfg.setName(Constants.IGNITE_DEFAULT_CACHE_NAME);
        cfg.setAtomicityMode(TRANSACTIONAL);
        cache = ignite.getOrCreateCache(cfg);
        objectCache = ignite.getOrCreateCache("CacheEntryObject");
    }


    /*public void databaseTest() throws InstantiationException, IllegalAccessException {
        //Initiate a cache dor SQL Datastore
        Connection connection = null;
        try {

            connection = new DataStoreDatabase().getConn();
            Statement stat;
            stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * from Books");
            while (rs.next()) {
                System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
            }
            } catch (SQLException e) {
                e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

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

    @Override
    public boolean storeValue(int key,CacheEntryObject cacheEntryObject) {
        System.out.println("current hash value storeValue() " + cacheEntryObject.hashCode());
        System.out.println("current key used to cache " + key);
        try {
            System.out.println("Value inside cache object " + cacheEntryObject.getObject().get("item"));
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

    @Override
    public CacheEntryObject retrieveObject(int key) {
        System.out.println("Current key to detokenize retrieveObject()" + key);
        try {
            System.out.println("Current key to detokenize retrieveObject()" + objectCache.get(key).getObject().get("item"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objectCache.get(key);
    }
}
