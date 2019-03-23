package com.securitybox.ediparser;


import org.json.JSONArray;
import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public abstract class EdiDocument implements EdiDocumentDao {

    String docType;
    @Override
    public String getDocumentType() {
        return docType;
    }

    public EdiDocument(String docType) {
        this.docType = docType;
    }
    Logger logger = Logger.getLogger(this.getClass().getName());

    public abstract String docuemntHandler(String method, JSONArray objectToBeTokenized, String message) throws JSONException, NoSuchAlgorithmException;
}

