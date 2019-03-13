package com.documenthandler.ediparser;

import org.json.JSONException;

public interface DocumentInterface {
    String docType = null;

    String getDocumentType();


    String convertToJson(String message) throws JSONException;
}
