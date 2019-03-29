package com.securitybox.tokenizer;

import com.securitybox.constants.Constants;
import com.securitybox.storage.CacheEntryObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TokenizerTest {


    public static void main(String args[]){
        Tokenizer tokenizer = new Tokenizer();
        final ArrayList<String> sender = new ArrayList<String>();
        sender.add("clientA");
        final ArrayList<String> receiver = new ArrayList<String>();
        receiver.add("clientB");

        CacheEntryObject cacheEntryObject = new CacheEntryObject() {
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
        try{
            //token based on integer type
            //create a JSON object with key "item"
             jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,"Value Stored");
             cacheEntryObject.setObject(jsonObjTemp);
             int token = tokenizer.tokenize(cacheEntryObject);
             CacheEntryObject newCa_0;
             newCa_0 = tokenizer.detokenize(token);
             assertEquals("Value Stored",newCa_0.getObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());

            //token based on String type
            jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,"Value Stored");
            cacheEntryObject.setObject(jsonObjTemp);
            String tokenStr = tokenizer.tokenize(cacheEntryObject,"Value Stored",127);
            CacheEntryObject newCa_1,newCa_2;
            newCa_1 = tokenizer.detokenize(tokenStr);
            assertEquals("Value Stored",newCa_1.getObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());
            //tokenizer test mixed values, insert as a cache object, retireve as a string token
            newCa_2 = tokenizer.detokenize(tokenStr.toString());
            assertEquals("Value Stored",newCa_2.getObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());

        } catch(Exception e) {
            e.printStackTrace();
        }


    }

}