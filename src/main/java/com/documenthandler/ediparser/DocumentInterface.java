package com.documenthandler.ediparser;

import org.json.JSONArray;
import org.json.JSONException;

public interface DocumentInterface {
    String docType = null;

    String getDocumentType();


    String convertToJson(String message, JSONArray objectsToBeTokenized) throws JSONException;
}
