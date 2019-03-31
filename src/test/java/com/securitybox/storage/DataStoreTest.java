package com.securitybox.storage;

import com.securitybox.constants.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;
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
        ArrayList<String> sender = new ArrayList<String>();
        sender.add("clientA");
        ArrayList<String> receiver = new ArrayList<String>();
        receiver.add("clientB");
        CacheEntryObject cacheEntryObject = new CacheEntryObject() {
            @Override
            public ArrayList getSenderIds() {
                return super.getSenderIds();
            }

            @Override
            public void setSenderIds(ArrayList senderIds) {
                super.setSenderIds(senderIds);
            }

            @Override
            public ArrayList getReceiverIds() {
                return super.getReceiverIds();
            }

            @Override
            public void setReceiverIds(ArrayList receiverIds) {
                super.setReceiverIds(receiverIds);
            }

            @Override
            public void setObject(JSONObject object) {
                super.setObject(object);
            }
        };

        JSONObject jsonObjTemp = new JSONObject();
        try {
            jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,storeValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cacheEntryObject.setObject(jsonObjTemp);
        dataStore.storeValue(token,cacheEntryObject);

    }

    @Test
    public void retrieveValue() {

        try {
            String result = dataStore.retrieveObject(token).getObject().getString(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME);
            assertEquals(storeValue,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}