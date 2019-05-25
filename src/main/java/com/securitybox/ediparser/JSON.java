package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import org.json.JSONArray;
import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class JSON extends EdiDocument {

    public  JSON(){
        super(Constants.DOCUMENT_TYPE_JSON);
    }
    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, String senderId, ArrayList<String> receiverIds, String recordDelimeter, String fieldDelimeter) throws JSONException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public boolean removeToken(String key) {
        return false;
    }

}
