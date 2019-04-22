package com.securitybox.models;

public class CSVRecord{
    String[] fields;
    String fieldDelimeter;

    //Constructor to split the CSV record into fields.
    public CSVRecord(String fieldDelimeter, String escapeChar,String record) {
        fields = record.split(fieldDelimeter,-1);
        this.fieldDelimeter = fieldDelimeter;
    }
    //Method to get number of elements in given record.
    public String getField(int i){
        return i<=fields.length ?fields[i]:null;
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
        return  fields.length;
    }

    //method to reconstruct and get the cvs record back.
    public String getRecord(){
        String ret="";
        for(int i=0;i< fields.length ; i++){
            if(i == fields.length -1)
                ret = ret + fields[i];
            else
                ret = ret + fields[i] + fieldDelimeter ;
        }

        return ret;

    }

}
