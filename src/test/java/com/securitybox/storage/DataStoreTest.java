package com.securitybox.storage;

import com.securitybox.constants.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataStoreTest {

    @Test
    public void storeValue() throws IllegalAccessException, InstantiationException {
        DataStore dataStore =  new DataStore();
        //System.out.println(dataStore.storeValue("1","myvalue"));
        //System.out.println(dataStore.retrieveValue("1"));
        //dataStore.databaseTest();


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
            jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,"value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cacheEntryObject.setObject(jsonObjTemp);
        dataStore.storeValue(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,cacheEntryObject);
        try {
            String result = dataStore.retrieveValue(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME);
            assertEquals("value",result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void retrieveValue() {
    }
}