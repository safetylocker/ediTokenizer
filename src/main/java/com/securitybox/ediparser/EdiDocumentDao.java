package com.securitybox.ediparser;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public interface EdiDocumentDao {
    String docType = null;

    String getDocumentType();

    String docuemntHandler(String method, JSONArray objectsToBeTokenized,String message) throws JSONException, NoSuchAlgorithmException;

}
