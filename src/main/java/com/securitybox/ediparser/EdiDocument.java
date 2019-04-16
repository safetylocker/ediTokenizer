package com.securitybox.ediparser;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public abstract class EdiDocument implements EdiDocumentDao {

    String docType;
    @Override
    public String getDocumentType() {
        return docType;
    }

    public EdiDocument(String docType) {
        this.docType = docType;
    }

    public abstract String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, String senderId, ArrayList<String> receiverIds, String recordDelimeter, String fieldDelimeter) throws JSONException, NoSuchAlgorithmException;
}

