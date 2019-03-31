package com.securitybox.tokenizer;

import com.securitybox.constants.Constants;
import com.securitybox.storage.AccessEntry;
import com.securitybox.storage.CacheEntryObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TokenizerTest {


    public static void main(String args[]) throws JSONException {
        Tokenizer tokenizer = new Tokenizer();
        final ArrayList<String> sender = new ArrayList<String>();
        sender.add("clientA");
        final ArrayList<String> receiver = new ArrayList<String>();
        receiver.add("clientB");
        CacheEntryObject newCaObj_1,newCaObj_2,newCaObj_3,newCaObj_4;
        final String cacheValue="My Value";

        CacheEntryObject cacheEntryObject_1 = new CacheEntryObject() {
            @Override
            public ArrayList getSenderIds() {
                return super.getSenderIds();
            }

            @Override
            public void setSenderIds(ArrayList arrayList) {
                super.setSenderIds(arrayList);
            }

            @Override
            public ArrayList getReceiverIds() {
                return super.getReceiverIds();
            }

            @Override
            public void setReceiverIds(ArrayList arrayList) {
                super.setReceiverIds(arrayList);
            }

            @Override
            public void setObject(JSONObject object) {
                super.setObject(object);
            }
        };

        CacheEntryObject cacheEntryObject_2 = new CacheEntryObject() {
            @Override
            public ArrayList getSenderIds() {
                return super.getSenderIds();
            }

            @Override
            public void setSenderIds(ArrayList arrayList) {
                super.setSenderIds(arrayList);
            }

            @Override
            public ArrayList getReceiverIds() {
                return super.getReceiverIds();
            }

            @Override
            public void setReceiverIds(ArrayList arrayList) {
                super.setReceiverIds(arrayList);
            }

            @Override
            public void setObject(JSONObject object) {
                super.setObject(object);
            }
        };

        CacheEntryObject cacheEntryObject_3 = new CacheEntryObject() {
            @Override
            public ArrayList getSenderIds() {
                return super.getSenderIds();
            }

            @Override
            public void setSenderIds(ArrayList arrayList) {
                super.setSenderIds(arrayList);
            }

            @Override
            public ArrayList getReceiverIds() {
                return super.getReceiverIds();
            }

            @Override
            public void setReceiverIds(ArrayList arrayList) {
                super.setReceiverIds(arrayList);
            }

            @Override
            public void setObject(JSONObject object) {
                super.setObject(object);
            }
        };

        CacheEntryObject cacheEntryObject_4 = new CacheEntryObject() {
            @Override
            public ArrayList getSenderIds() {
                return super.getSenderIds();
            }

            @Override
            public void setSenderIds(ArrayList arrayList) {
                super.setSenderIds(arrayList);
            }

            @Override
            public ArrayList getReceiverIds() {
                return super.getReceiverIds();
            }

            @Override
            public void setReceiverIds(ArrayList arrayList) {
                super.setReceiverIds(arrayList);
            }

            @Override
            public void setObject(JSONObject object) {
                super.setObject(object);
            }
        };

        JSONObject jsonObjTemp = new JSONObject();
        jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,cacheValue);

        try {
            //token based on integer type
            cacheEntryObject_1.setObject(jsonObjTemp);
            int token = tokenizer.tokenize(cacheEntryObject_1);
            newCaObj_1 = tokenizer.deTokenize(Integer.toString(token));
            assertEquals(cacheValue, newCaObj_1.getObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());

            //token based on String type
            cacheEntryObject_2.setObject(jsonObjTemp);
            String tokenStr_1 = tokenizer.tokenize(cacheEntryObject_2, cacheValue, 127);
            newCaObj_2 = tokenizer.deTokenize(tokenStr_1);
            assertEquals(cacheValue, newCaObj_2.getObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());

            //tokenizer test mixed values, insert as a cache object as integer, retireve as a string key
            cacheEntryObject_3.setObject(jsonObjTemp);
            int tokenStr_2 = tokenizer.tokenize(cacheEntryObject_3);
            newCaObj_3 = tokenizer.deTokenize(Integer.toString(tokenStr_2));
            assertEquals(cacheValue, newCaObj_3.getObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());


            //tokeizer test with client access log

            cacheEntryObject_4.setObject(jsonObjTemp);
            int tokenStr_4 = tokenizer.tokenize(cacheEntryObject_4);
            newCaObj_4 = tokenizer.deTokenize(String.valueOf(tokenStr_4), "clientId_1");
            newCaObj_4 = tokenizer.deTokenize(String.valueOf(tokenStr_4), "clientId_2");

            System.out.println(newCaObj_4.getObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());
            for (int i = 0; i < newCaObj_4.getAccessLogs().size(); i++) {
                    AccessEntry ae = (AccessEntry)newCaObj_4.getAccessLogs().get(i);
                    System.out.println("Client :  " + ae.getClientId() + " accessed token "+ tokenStr_4 + " at " + ae.getAcccesTime() );

            }


        }catch (Exception e){
            e.getCause();
        }

    }

}