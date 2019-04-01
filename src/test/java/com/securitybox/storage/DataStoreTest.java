package com.securitybox.storage;

import com.securitybox.constants.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataStoreTest {

    DataStore dataStore;
    final String storeValue ="Data item to be stored";
    String token="38400000-8cf0-11bd-b23e-10b96e4ef00d";
    public DataStoreTest(){
        dataStore =  new DataStore();
    }

    @Test
    public void storeValue() throws IllegalAccessException, InstantiationException {

       //Test the cacheobject injection
        String senderId = "clientA";
        ArrayList<String> receiverIds = new ArrayList<String>();
        receiverIds.add("clientB");
        JSONObject jsonObjTemp = new JSONObject();
        try {
            jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,storeValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CacheEntryObject cacheEntryObject = new CacheEntryObject(senderId,receiverIds,jsonObjTemp);
        assertEquals(true,dataStore.storeValue(token,cacheEntryObject));

    }

    @Test
    public void retrieveValue() {

        try {
            assertEquals(storeValue,dataStore.retrieveObject(token).getJsonObject().getString(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME));
            assertEquals(storeValue,dataStore.retrieveObject(token,"ID").getJsonObject().getString(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}