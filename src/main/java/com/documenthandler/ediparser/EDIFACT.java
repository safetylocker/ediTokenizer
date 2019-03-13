package com.documenthandler.ediparser;


import com.documenthandler.constants.Constants;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class EDIFACT extends EdiDocument {

    public EDIFACT(){
        super(Constants.DOCUMENT_TYPE_EDIFACT);

    }

    @Override
    public String convertToJson(String message) throws JSONException {
        String response="";
        response =  getEdiSepration(
                    getEdiSepration(
                    getEdiSepration(message,Constants.EDIFACT_SEGMENT_TERMINATOR),
                            Constants.EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR),
                            Constants.EDIFACT_DATA_ELEMENT_SEPERATOR);
        return response;
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

