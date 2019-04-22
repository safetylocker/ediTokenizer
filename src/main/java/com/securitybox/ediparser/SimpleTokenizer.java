package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import com.securitybox.models.CacheEntryObject;
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
        return tokenizer.tokenize(message,maxLenght,senderId,receiverIds) ;
    }

    public String deTokenizeSingleValue(String method,String token, String senderId) throws JSONException, NoSuchAlgorithmException {
        CacheEntryObject cacheEntryObject = tokenizer.deTokenize(token, senderId);
        if (cacheEntryObject.isErrorExists()==false) {
                return cacheEntryObject.getJsonObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString();
        } else {
            return cacheEntryObject.getErrorEntries().toString();
        }
    }

    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, String senderId, ArrayList<String> receiverIds) throws JSONException, NoSuchAlgorithmException {
        return null;
    }

    public boolean removeToken(String key){
        return tokenizer.removeToken(key);
    }

    public boolean removeTokenEntry(String key,String senderId){
        return tokenizer.removeTokenEntry(key,senderId);
    }

    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, String senderId, ArrayList<String> receiverIds, String recordDelimeter, String fieldDelimeter) throws JSONException, NoSuchAlgorithmException {
        return null;
    }
}

