package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import com.securitybox.storage.CacheEntryObject;
import com.securitybox.tokenizer.Tokenizer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class EDIFACT extends EdiDocument {

    public Tokenizer tokenizer = new Tokenizer();
    public EDIFACT() throws NoSuchAlgorithmException {
        super(Constants.DOCUMENT_TYPE_EDIFACT);
    }


    @Override
    public String docuemntHandler(String method,JSONArray objectToBeTokenized,String message) throws JSONException, NoSuchAlgorithmException {
        String response = "";
        String EDIFACT_SEGMENT_TERMINATOR="";
        String EDIFACT_DATA_ELEMENT_SEPERATOR="";
        String EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR="";
        String segmentUNA="";
        //Identify the EDIFACT seperators from the incoming message

            //Extract the UNA segment if exists
        try {
            segmentUNA = message.substring(0, message.indexOf("UNB"));
            EDIFACT_SEGMENT_TERMINATOR = segmentUNA.substring(8,9);
            EDIFACT_DATA_ELEMENT_SEPERATOR = segmentUNA.substring(4,5);
            EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR=segmentUNA.substring(3,4);
        }catch(Exception e){
            EDIFACT_SEGMENT_TERMINATOR=Constants.EDIFACT_SEGMENT_TERMINATOR;
            EDIFACT_DATA_ELEMENT_SEPERATOR = Constants.EDIFACT_DATA_ELEMENT_SEPERATOR;
            EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR= Constants.EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR;
        }

        System.out.println("EDIFACT_SEGMENT_TERMINATOR :" + EDIFACT_SEGMENT_TERMINATOR);
        System.out.println("EDIFACT_DATA_ELEMENT_SEPERATOR :" + EDIFACT_DATA_ELEMENT_SEPERATOR);
        System.out.println("EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR :" + EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR);


        JSONArray segmentArr = seperateElements(message,EDIFACT_SEGMENT_TERMINATOR);
        //Iterate through all the segments
        for (int i = 0; i < segmentArr.length(); i++){
            JSONArray componentArr = seperateElements(segmentArr.get(i).toString(),EDIFACT_DATA_ELEMENT_SEPERATOR);
            //iterator thorugh all components inside the segment
            for (int j = 0; j < componentArr.length(); j++){
                JSONArray dataElementArray = seperateElements(componentArr.get(j).toString(), EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR);
                //iterator trhough all data elements inside a component
                for (int k = 0; k < dataElementArray.length(); k++){
                    //check and tokenize if the value now in concern is requested to be tokenized
                    for(int ja=0 ; ja < objectToBeTokenized.length();ja++){
                        //Create temproary JSON object to handle the current content.
                        JSONObject requestedElements = objectToBeTokenized.getJSONObject(ja);
                        if(requestedElements.getInt(Constants.EDIFACT_SEGMENT_NUMBER)== i+1 && requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_NUMBER )== j+1 && requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_POSITION) == k+1 )  {
                                    JSONObject jsonObjTemp = new JSONObject();
                                    if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_TOKENIZE)) {
                                        jsonObjTemp.put("item", tokenizer.tokenize(dataElementArray.get(k).toString(), requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_LENGTH)));
                                        System.out.println("Tokenized Value/Token : "  + dataElementArray.get(k).toString() + "/"  + jsonObjTemp.get("item")) ;
                                  }else if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_DETOKENIZE)) {
                                        System.out.println("Detokenized Token/Value : "  + dataElementArray.get(k).toString() +  "/"  + tokenizer.deTokenize(Integer.parseInt(dataElementArray.get(k).toString())));
                                        jsonObjTemp.put("item", tokenizer.deTokenize(Integer.parseInt(dataElementArray.get(k).toString())));
                                  }else{
                                        //DO nothing
                                    }
                                    //set the results from temporary JSON object back to current position
                                    //this will either write back the entry with token or de-tokenized string
                                    dataElementArray.put(k,jsonObjTemp.get("item"));
                        }

                    }
                    if(k > 0 )
                        response = response + EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR + dataElementArray.get(k).toString();
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

    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, final ArrayList<String> senderIds, final ArrayList<String> receiverIds) throws JSONException, NoSuchAlgorithmException {
        String response = "";
        String EDIFACT_SEGMENT_TERMINATOR="";
        String EDIFACT_DATA_ELEMENT_SEPERATOR="";
        String EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR="";
        String segmentUNA="";
        //Identify the EDIFACT seperators from the incoming message

        //Extract the UNA segment if exists
        try {
            segmentUNA = message.substring(0, message.indexOf("UNB"));
            EDIFACT_SEGMENT_TERMINATOR = segmentUNA.substring(8,9);
            EDIFACT_DATA_ELEMENT_SEPERATOR = segmentUNA.substring(4,5);
            EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR=segmentUNA.substring(3,4);
        }catch(Exception e){
            EDIFACT_SEGMENT_TERMINATOR=Constants.EDIFACT_SEGMENT_TERMINATOR;
            EDIFACT_DATA_ELEMENT_SEPERATOR = Constants.EDIFACT_DATA_ELEMENT_SEPERATOR;
            EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR= Constants.EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR;
        }

        System.out.println("EDIFACT_SEGMENT_TERMINATOR :" + EDIFACT_SEGMENT_TERMINATOR);
        System.out.println("EDIFACT_DATA_ELEMENT_SEPERATOR :" + EDIFACT_DATA_ELEMENT_SEPERATOR);
        System.out.println("EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR :" + EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR);

        JSONArray segmentArr = seperateElements(message,EDIFACT_SEGMENT_TERMINATOR);
        //Iterate through all the segments
        for (int i = 0; i < segmentArr.length(); i++){
            JSONArray componentArr = seperateElements(segmentArr.get(i).toString(),EDIFACT_DATA_ELEMENT_SEPERATOR);
            //iterator thorugh all components inside the segment
            for (int j = 0; j < componentArr.length(); j++){
                JSONArray dataElementArray = seperateElements(componentArr.get(j).toString(), EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR);
                //iterator trhough all data elements inside a component
                for (int k = 0; k < dataElementArray.length(); k++){
                    //check and tokenize if the value now in concern is requested to be tokenized
                    for(int ja=0 ; ja < objectsToBeTokenized.length();ja++){
                        JSONObject requestedElements = objectsToBeTokenized.getJSONObject(ja);
                        if(requestedElements.getInt(Constants.EDIFACT_SEGMENT_NUMBER)== i+1 && requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_NUMBER )== j+1 && requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_POSITION) == k+1 )  {
                            //Create temproary JSON object to handle the current content.
                            JSONObject jsonObjTemp = new JSONObject();
                            jsonObjTemp.put("item",dataElementArray.get(k));
                            //initialize cashe object item with the data to be written
                            if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_TOKENIZE)) {
                                CacheEntryObject cacheEntryObject = new CacheEntryObject() {
                                    @Override
                                    public ArrayList getSenderIds() {
                                        return super.getSenderIds();
                                    }

                                    @Override
                                    public void setSenderIds(ArrayList senderIds) {
                                        super.setSenderIds(senderIds);
                                    }

                                    @Override
                                    public ArrayList getReceiverIds() {
                                        return super.getReceiverIds();
                                    }

                                    @Override
                                    public void setReceiverIds(ArrayList receiverIds) {
                                        super.setReceiverIds(receiverIds);
                                    }

                                    @Override
                                    public void setObject(JSONObject object) {
                                        super.setObject(object);
                                    }
                                };

                                //set the object to be included in the cacheEntryObject

                                cacheEntryObject.setObject(jsonObjTemp);
                                //call tokernization service with cacheObject to be tokenized
                                jsonObjTemp.put("item",tokenizer.tokenize(cacheEntryObject));

                            }else if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_DETOKENIZE)) {
                                //get hte key to be retrived from current message
                                System.out.println("current key to be de-tokenized " + dataElementArray.get(k).toString());
                                //retreive the cacheentry object from the cache
                                CacheEntryObject tmpCacheEntryObject = tokenizer.detokenize(Integer.valueOf(dataElementArray.get(k).toString()));
                                //retierve the values from the object stored in the cache object.
                                if(tmpCacheEntryObject==null)
                                    jsonObjTemp.put("item",dataElementArray.get(k).toString());
                                else
                                    jsonObjTemp.put("item",tmpCacheEntryObject.getObject().get("item"));

                            }else{
                                //DO nothing
                            }
                            //put back the retrieved values from the cached object/key received from tokenization to element position back
                            dataElementArray.put(k,jsonObjTemp.get("item"));
                        }

                    }
                    //collect back the response to create the EDIFACT message back
                    if(k > 0 )
                        response = response + EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR + dataElementArray.get(k).toString();
                    else
                        response = response + dataElementArray.get(k).toString();
                }

                //collect back the response to create the EDIFACT message back
                if(j < componentArr.length()-1)
                    response = response + EDIFACT_DATA_ELEMENT_SEPERATOR;
            }
            ///collect back the response to create the EDIFACT message back
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

