package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import com.securitybox.storage.CacheEntryObject;
import com.securitybox.tokenizer.Tokenizer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class SimpleTokenizer extends EdiDocument {

    public Tokenizer tokenizer = new Tokenizer();

    public SimpleTokenizer() {
        super(Constants.DOCUMENT_TYPE_SINGLE_VALUE_TOKEN);
    }


    public String tokenizeSingleValue(String method,String message, String senderId, ArrayList<String> receiverIds,int maxLenght) throws JSONException, NoSuchAlgorithmException {
        String response="";

        //Create temproary JSON object to handle the current content.
        JSONObject jsonObjTemp = new JSONObject();
        jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,message);
        //initialize cashe object item with the data to be written
        if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_TOKENIZE)) {
            CacheEntryObject cacheEntryObject = new CacheEntryObject(senderId,receiverIds,jsonObjTemp);

            //set the object to be included in the cacheEntryObject

            cacheEntryObject.setJsonObject(jsonObjTemp);
            response = tokenizer.tokenize(cacheEntryObject,message,maxLenght);
        }
        return response ;
    }

    public String deTokenizeSingleValue(String method,String token, String senderId, ArrayList<String> receiverIds) throws JSONException, NoSuchAlgorithmException {
        return  tokenizer.deTokenize(token,senderId).getJsonObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString();
    }


    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, String senderId, ArrayList<String> receiverIds) throws JSONException, NoSuchAlgorithmException {
        return null;
    }
}

