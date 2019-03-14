package com.securitybox.ediparser;


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
}

