package com.securitybox.storage;

import com.securitybox.constants.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class DataStoreAccessRightsTest {

    DataStore dataStore;
    final String storeValue ="Data item to be stored";
    String token;
    ArrayList<String> receiverIds;
    String senderId;
    JSONObject jsonObjTemp;

    public DataStoreAccessRightsTest(){
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
    public void removeTokenEntry() throws JSONException {
        CacheEntryObject cacheEntryObject = new CacheEntryObject(senderId,receiverIds,jsonObjTemp);
        token = UUID.randomUUID().toString();
        dataStore.storeValue(token,cacheEntryObject,senderId);
        //test if token can be removed by another client Id
        assertFalse(dataStore.removeTokenEntry(token,"Sender_1234"));
        //test if token can be removed by same client Id
        assertTrue(dataStore.removeTokenEntry(token,senderId));
    }
}