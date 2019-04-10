package com.securitybox.storage;

import com.securitybox.constants.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class DataStoreTest {

    DataStore dataStore;
    final String storeValue ="Data item to be stored";
    String token="38400000-8cf0-11bd-b23e-10b96e4ef00d";
    ArrayList<String> receiverIds;
    String senderId;
    JSONObject jsonObjTemp;

    public DataStoreTest(){
        dataStore =  new DataStore();
        senderId = "clientA";
        receiverIds = new ArrayList<String>();
        receiverIds.add("clientB");
        jsonObjTemp = new JSONObject();
        try {
            jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,storeValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

 @Test
    public void storeValue() throws IllegalAccessException, InstantiationException {

       //Test the cacheobject injection
        CacheEntryObject cacheEntryObject = new CacheEntryObject(senderId,receiverIds,jsonObjTemp);
        assertEquals(true,dataStore.storeValue(token,cacheEntryObject));

    }

 @Test
    public void retrieveValue() {

        try {
            assertEquals(storeValue,dataStore.retrieveObject(token,"C1").getJsonObject().getString(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME));
            assertEquals(storeValue,dataStore.retrieveObject(token,"C2").getJsonObject().getString(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeToken() {
    }

    @Test
    public void removeTokenEntry() throws JSONException {
        CacheEntryObject cacheEntryObject = new CacheEntryObject(senderId,receiverIds,jsonObjTemp);
        token = UUID.randomUUID().toString();
        dataStore.storeValue(token,cacheEntryObject);
        dataStore.removeTokenEntry(token,senderId);
        cacheEntryObject = dataStore.retrieveObject(token,"C1");
        assertEquals(Constants.IGNITE_DEFAULT_VALUE_DELETED,cacheEntryObject.getJsonObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());

        String tokenString = UUID.randomUUID().toString();
        dataStore.storeValue(tokenString,cacheEntryObject);
        dataStore.removeTokenEntry(tokenString,senderId);
        cacheEntryObject = dataStore.retrieveObject(tokenString,"C!");
        assertEquals(Constants.IGNITE_DEFAULT_VALUE_DELETED,cacheEntryObject.getJsonObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());

    }
}