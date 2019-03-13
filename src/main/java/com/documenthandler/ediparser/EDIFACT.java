package com.documenthandler.ediparser;


import com.documenthandler.constants.Constants;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class EDIFACT extends EdiDocument {


    @Override
    public void setDocType() {
        setDocType(Constants.DOCUMENT_TYPE_EDIFACT);
    }

    @Override
    public void setDocType(String docType) {

    }

    @Override
    public void setDocDelimeters() {

    }

    @Override
    public String convertToJson(String message) throws JSONException {
        String response="";
        response = getEdiSepration(getEdiSepration(message,Constants.EDIFACT_SEGMENT_TERMINATOR),Constants.EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR);
        return response;
    }

    @Override
    public String convertToEdiDocument() {
        return null;
    }

    public String getEdiSepration(String segment,String delimeter) throws JSONException {

        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(segment.split("\\"+delimeter)));
        JSONArray jsonArray= new JSONArray();
        String response="";
        int currentPos=0;
        while(arrayList.size() > currentPos){
            jsonArray.put(currentPos,arrayList.get(currentPos));
            currentPos++;
        }

        for(int i = 0;i<jsonArray.length();i++ ){
            if(i > 0)
                response +=  delimeter + jsonArray.get(i).toString();
            else
                response +=  jsonArray.get(i).toString();
        }

        return response;
    }

}

