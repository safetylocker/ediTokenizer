package com.securitybox.ediparser;

class CSVRecord{
    String[] fields;
    String fieldDelimeter;

    public CSVRecord(String fieldDelimeter, String escapeChar,String record) {

        fields = record.split(fieldDelimeter);
        this.fieldDelimeter = fieldDelimeter;
    }

    public String getField(int i){
        return i<=fields.length ?fields[i]:null;
    }

    public boolean setFiled(int pos,String s) {
        if (pos <= fields.length) {
            fields[pos] = s;
            return true;
        } else {
            return false;
        }
    }

    public int getCount(){
        return  fields.length;
    }

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
