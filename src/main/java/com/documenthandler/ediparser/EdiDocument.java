package com.documenthandler.ediparser;


import java.util.HashMap;

public abstract class EdiDocument implements DocumentInterface {

    String docType;
    @Override
    public String getDocumentType() {
        return docType;
    }

    public EdiDocument(String docType) {
        this.docType = docType;
    }
}

