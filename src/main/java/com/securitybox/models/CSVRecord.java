package com.securitybox.models;

import org.apache.commons.text.StringTokenizer;
import org.apache.log4j.Logger;

public class CSVRecord{
    String[] fields;
    String fieldDelimeter;
    StringTokenizer stringTokenizer;
    final Logger logger = Logger.getLogger(this.getClass().getName());

    //Constructor to split the CSV record into fields.
    public CSVRecord(String fieldDelimeter, String escapeChar,String record) {

        this.fieldDelimeter = fieldDelimeter;
        stringTokenizer = new StringTokenizer(record,fieldDelimeter);
        stringTokenizer.setIgnoreEmptyTokens(false);
        //fields = record.split("\\"+this.fieldDelimeter );
        fields = stringTokenizer.getTokenArray();
    }
    //Method to get number of elements in given record.
    public String getField(int i){
        return i<fields.length ?fields[i]:null;
    }

    //method to set field in CSV record.
    public boolean setFiled(int pos,String s) {
        if (pos <= fields.length) {
            fields[pos] = s;
            return true;
        } else {
            return false;
        }
    }

    //method to get fields length.
    public int getCount(){
        return  fields.length +1;
    }

    //method to reconstruct and get the cvs record back.
    public String getRecord(){
        String ret="";
        for(int i=0;i< fields.length ; i++){
            if(i == (fields.length -1)) {
                ret = ret + fields[i];
            }else {
                ret = ret + fields[i] + this.fieldDelimeter;
            }
        }
        return ret;

    }

}
