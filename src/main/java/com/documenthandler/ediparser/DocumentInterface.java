package com.documenthandler.ediparser;

import org.json.JSONException;

public interface DocumentInterface {
    String docType = null;
    String getDocumentType();
    DelimeterInterface delimeterInterface=null;

    void setDocType(String docType);
    String getDocType();
    void setDocDelimeters();
    String convertToJson(String message) throws JSONException;
    String convertToEdiDocument();
}
