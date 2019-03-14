package com.documenthandler.ediparser;

import com.documenthandler.constants.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class EDIFACT extends EdiDocument {

    public EDIFACT(){
        super(Constants.DOCUMENT_TYPE_EDIFACT);
    }

    @Override
    public String convertToJson(String message,JSONArray objectToBeTokenized) throws JSONException {
        String response = "";
        JSONArray segmentArr = getEdiSepration(message, Constants.EDIFACT_SEGMENT_TERMINATOR);
        //Iterate through all the segments
        for (int i = 0; i < segmentArr.length(); i++){
            JSONArray componentArr = getEdiSepration(segmentArr.get(i).toString(), Constants.EDIFACT_DATA_ELEMENT_SEPERATOR);
            //iterator thorugh all components inside the segment
            for (int j = 0; j < componentArr.length(); j++){
                JSONArray dataElementArray = getEdiSepration(componentArr.get(j).toString(), Constants.EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR);
                //iterator trhough all data elements inside a component
                for (int k = 0; k < dataElementArray.length(); k++){
                    //check and tokenize if the value now in concern is requested to be tokenized
                    for(int ja=0 ; ja < objectToBeTokenized.length();ja++){
                        JSONObject tmp = objectToBeTokenized.getJSONObject(ja);
                        if(tmp.getInt(Constants.EDIFACT_SEGMENT_NUMBER)== i+1 && tmp.getInt(Constants.EDIFACT_DATA_ELEMENT_NUMBER )== j+1 && tmp.getInt(Constants.EDIFACT_DATA_ELEMENT_POSITION) == k+1 )  {
                                    System.out.println("HIT FOUND : " + dataElementArray.get(k).toString());
                        }

                    }
                    if(k > 0 )
                        response = response + Constants.EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR + dataElementArray.get(k).toString();
                    else
                        response = response + dataElementArray.get(k).toString();
                }

                if(j < componentArr.length()-1)
                    response = response + Constants.EDIFACT_DATA_ELEMENT_SEPERATOR;
            }

            response = response + Constants.EDIFACT_SEGMENT_TERMINATOR;

        }
        return response;
    }


    public JSONArray getEdiSepration(String input,String delimeter) throws JSONException {
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(input.split("\\"+delimeter)));
        JSONArray jsonArray= new JSONArray();

        String response="";
        int currentPos=0;
        //break the segment
        while(arrayList.size() > currentPos){
            jsonArray.put(currentPos,arrayList.get(currentPos));
            currentPos++;
        }

        //rebuild the response to be returned
        for(int i = 0;i<jsonArray.length();i++ ){
            if(i > 0)
                response +=  delimeter + jsonArray.get(i).toString();
            else
                response +=  jsonArray.get(i).toString();
        }
        return jsonArray;
    }

}

