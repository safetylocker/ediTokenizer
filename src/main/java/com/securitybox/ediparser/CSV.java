package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.json.JSONArray;
import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class CSV extends EdiDocument {
    public CSV(String docType) {
        super(Constants.DOCUMENT_TYPE_CSV);
    }

    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message) throws JSONException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, ArrayList<String> senderIds, ArrayList<String> receiverIds) throws JSONException, NoSuchAlgorithmException {
        return null;
    }
}
