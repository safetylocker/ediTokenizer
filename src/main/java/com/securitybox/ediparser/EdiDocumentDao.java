package com.securitybox.ediparser;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Logger;

public interface EdiDocumentDao {
    String docType = null;
    //method to remove a token from the storage
    boolean removeToken(String key);
    //Method to get the document type
    String getDocumentType();
    //Method to handle the tokenization/de-tokenization of edi document
    String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, String senderId, ArrayList<String> receiverIds) throws JSONException, NoSuchAlgorithmException;


}
