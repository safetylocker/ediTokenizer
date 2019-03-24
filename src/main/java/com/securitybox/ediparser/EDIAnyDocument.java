package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import org.json.JSONArray;
import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class EDIAnyDocument  extends  EdiDocument{
    public EDIAnyDocument(String docType) {
        super(Constants.DOCUMENT_TYPE_ALL);
    }

    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, ArrayList<String> senderIds, ArrayList<String> receiverIds) throws JSONException, NoSuchAlgorithmException {
        return null;
    }
}
