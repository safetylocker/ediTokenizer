package com.securitybox.ediparser;

public abstract class EdiDocument implements EdiDocumentDao {

    String docType;
    @Override
    public String getDocumentType() {
        return docType;
    }

    public EdiDocument(String docType) {
        this.docType = docType;
    }
}

