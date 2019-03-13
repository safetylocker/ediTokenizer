package com.documenthandler.ediparser;


import java.util.HashMap;

public abstract class EdiDocument implements DocumentInterface,DelimeterInterface {

    @Override
    public String getDocumentType() {
        return docType;
    }

    public abstract void setDocType();

    public abstract void setDocType(String docType);

    @Override
    public String getDocType() {
        return docType;
    }

    private void setDelimeters(HashMap<String,String> delimeterInterface){
        delimeterInterface = delimeterInterface;
    }
}

