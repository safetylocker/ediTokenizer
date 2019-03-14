package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import com.securitybox.tokenizer.Tokenizer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class EDIFACT extends EdiDocument {

    Tokenizer tokenizer = new Tokenizer();
    public EDIFACT() throws NoSuchAlgorithmException {
        super(Constants.DOCUMENT_TYPE_EDIFACT);
    }

    @Override
    public String docuemntHandler(String method,JSONArray objectToBeTokenized,String message) throws JSONException, NoSuchAlgorithmException {
        String response = "",EDIFACT_SEGMENT_TERMINATOR="",EDIFACT_DATA_ELEMENT_SEPERATOR="",segmentUNA="";
        //Identify the EDIFACT seperators from the incoming message
        try {
            //Extract the UNA segment if exists
            segmentUNA = message.substring(0, message.indexOf("UNB"));
            EDIFACT_SEGMENT_TERMINATOR = segmentUNA.substring(6,7);
            EDIFACT_DATA_ELEMENT_SEPERATOR = segmentUNA.substring(2,3);
        }catch (Exception e){
            //if not use the default stadard seperators
            EDIFACT_SEGMENT_TERMINATOR = Constants.EDIFACT_SEGMENT_TERMINATOR;
            EDIFACT_DATA_ELEMENT_SEPERATOR = Constants.EDIFACT_DATA_ELEMENT_SEPERATOR;
        }


        JSONArray segmentArr = seperateElements(message,EDIFACT_SEGMENT_TERMINATOR);
        //Iterate through all the segments
        for (int i = 0; i < segmentArr.length(); i++){
            JSONArray componentArr = seperateElements(segmentArr.get(i).toString(),EDIFACT_DATA_ELEMENT_SEPERATOR);
            //iterator thorugh all components inside the segment
            for (int j = 0; j < componentArr.length(); j++){
                JSONArray dataElementArray = seperateElements(componentArr.get(j).toString(), Constants.EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR);
                //iterator trhough all data elements inside a component
                for (int k = 0; k < dataElementArray.length(); k++){
                    //check and tokenize if the value now in concern is requested to be tokenized
                    for(int ja=0 ; ja < objectToBeTokenized.length();ja++){
                        JSONObject requestedElements = objectToBeTokenized.getJSONObject(ja);
                        if(requestedElements.getInt(Constants.EDIFACT_SEGMENT_NUMBER)== i+1 && requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_NUMBER )== j+1 && requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_POSITION) == k+1 )  {
                                    JSONObject jsonObjTemp = new JSONObject();
                                    if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_TOKENIZE)) {
                                        jsonObjTemp.put("item", tokenizer.tokenize(dataElementArray.get(k).toString(), requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_LENGTH)));
                                        System.out.println("Tokenized Value/Token : "  + dataElementArray.get(k).toString() + "/"  + jsonObjTemp.get("item")) ;
                                  }else if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_DETOKENIZE)) {
                                        System.out.println("Detokenized Token/Value : "  + dataElementArray.get(k).toString() +  "/"  + tokenizer.deTokenize(dataElementArray.get(k).toString())) ;
                                        jsonObjTemp.put("item", tokenizer.deTokenize(dataElementArray.get(k).toString()));
                                  }else{
                                        //DO nothing
                                    }

                                    dataElementArray.put(k,jsonObjTemp.get("item"));
                        }

                    }
                    if(k > 0 )
                        response = response + Constants.EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR + dataElementArray.get(k).toString();
                    else
                        response = response + dataElementArray.get(k).toString();
                }

                if(j < componentArr.length()-1)
                    response = response + EDIFACT_DATA_ELEMENT_SEPERATOR;
            }

            response = response + EDIFACT_SEGMENT_TERMINATOR;

        }
        return response;
    }


    public JSONArray seperateElements(String input, String delimeter) throws JSONException {
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

