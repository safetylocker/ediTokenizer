package com.securitybox.tokenizer;

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
        try {
            jsonObjTemp.put("mykey","myvalue");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cacheEntryObject.setObject(jsonObjTemp);
        tokenizer.tokenize(cacheEntryObject);
        try {
            CacheEntryObject newCa;
            newCa = tokenizer.detokenize(cacheEntryObject.hashCode());
            System.out.println(
                    newCa.getObject().get("mykey")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}