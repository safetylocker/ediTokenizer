package com.securitybox.ediparser;

class CSVRecord{
    public CSVRecord(String delFields, String escapeChar, boolean isHeaderSkipped) {
        this.delFields = delFields;
        this.escapeChar = escapeChar;
        this.isHeaderSkipped = isHeaderSkipped;
    }

    public String getDelFields() {
        return delFields;
    }

    public void setDelFields(String delFields) {
        this.delFields = delFields;
    }

    public String getEscapeChar() {
        return escapeChar;
    }

    public void setEscapeChar(String escapeChar) {
        this.escapeChar = escapeChar;
    }

    public boolean isHeaderSkipped() {
        return isHeaderSkipped;
    }

    public void setHeaderSkipped(boolean headerSkipped) {
        isHeaderSkipped = headerSkipped;
    }

    private String delEndOfLine,delFields,escapeChar;
    boolean isHeaderSkipped;
}
