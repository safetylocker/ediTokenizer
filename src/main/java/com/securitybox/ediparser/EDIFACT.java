package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import com.securitybox.models.CacheEntryObject;
import com.securitybox.tokenizer.Tokenizer;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class EDIFACT extends EdiDocument {

    public Tokenizer tokenizer = new Tokenizer();
    final Logger logger = Logger.getLogger(this.getClass().getName());

    public EDIFACT() throws NoSuchAlgorithmException {
        super(Constants.DOCUMENT_TYPE_EDIFACT);
    }

    @Override
    public boolean removeToken(String key){
        return tokenizer.removeToken(key);
    }

    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, String senderId, ArrayList<String> receiverIds,String recordDelimeter,String fieldDelimeter) throws JSONException, NoSuchAlgorithmException {
        String response = "";
        String EDIFACT_SEGMENT_TERMINATOR="";
        String EDIFACT_DATA_ELEMENT_SEPERATOR="";
        String EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR="";
        String segmentUNA="";
        String EDIFACT_DECIMAL_SEPERATOR="";
        //Identify the EDIFACT seperators from the incoming message

        //Extract the UNA segment if exists
        try {
            segmentUNA = message.substring(0, message.indexOf("UNB"));
            EDIFACT_SEGMENT_TERMINATOR = segmentUNA.substring(8,9);
            EDIFACT_DATA_ELEMENT_SEPERATOR = segmentUNA.substring(4,5);
            EDIFACT_DECIMAL_SEPERATOR = segmentUNA.substring(5,6);
            EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR=segmentUNA.substring(3,4);

        }catch(Exception e){
            EDIFACT_SEGMENT_TERMINATOR=Constants.EDIFACT_SEGMENT_TERMINATOR;
            EDIFACT_DATA_ELEMENT_SEPERATOR = Constants.EDIFACT_DATA_ELEMENT_SEPERATOR;
            EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR= Constants.EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR;
            EDIFACT_DECIMAL_SEPERATOR = Constants.EDIFACT_DECIMAL_SEPERATOR;
        }

        logger.debug("EDIFACT_SEGMENT_TERMINATOR :" + EDIFACT_SEGMENT_TERMINATOR);
        logger.debug("EDIFACT_DATA_ELEMENT_SEPERATOR :" + EDIFACT_DATA_ELEMENT_SEPERATOR);
        logger.debug("EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR :" + EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR);
        logger.debug("EDIFACT_DECIMAL_SEPERATOR :" + EDIFACT_DECIMAL_SEPERATOR);

        JSONArray segmentArr = seperateElements(message,EDIFACT_SEGMENT_TERMINATOR);
        //Iterate through all the segments
        for (int i = 0; i < segmentArr.length(); i++){
            //seperate segments using segment seperator
            JSONArray componentArr = seperateElements(segmentArr.get(i).toString(),EDIFACT_DATA_ELEMENT_SEPERATOR);
            logger.debug("Handling segment " + segmentArr.get(i).toString());
            //iterator thorugh all components inside the segment
            for (int j = 0; j < componentArr.length(); j++){
                //seperate elements using composite element seperator
                JSONArray dataElementArray = seperateElements(componentArr.get(j).toString(), EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR);
                logger.debug("Handling composite element " + componentArr.get(j).toString());
                //iterator trhough all data elements inside a component
                for (int k = 0; k < dataElementArray.length(); k++){
                    logger.debug("Checking element : " + dataElementArray.getString(k));
                    //check and tokenize if the value now in concern is requested to be tokenized in JSON array objects
                    for(int ja=0 ; ja < objectsToBeTokenized.length();ja++){
                        JSONObject requestedElements = objectsToBeTokenized.getJSONObject(ja);
                        //check if segment number is defined in object to be tokenized, priority is given to segment number
                        if(requestedElements.has(Constants.EDIFACT_SEGMENT_NUMBER)) {
                            //check and tokenize the element if found as requested
                            if (requestedElements.getInt(Constants.EDIFACT_SEGMENT_NUMBER) == (i + 1)//if segment number is matched
                                    && requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_NUMBER) == (j + 1) //and if composite element number is matched
                                    && requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_POSITION) == (k + 1)) {//and if element position is matched
                                logger.debug("Segment Number found to be tokenized " + requestedElements.getInt(Constants.EDIFACT_SEGMENT_NUMBER));
                                edifactTokenization(dataElementArray, k, method, senderId, receiverIds, requestedElements);
                            }
                        }//if segment name is specified
                        else if(requestedElements.has(Constants.EDIFACT_SEGMENT_NAME)){  //If segment name exists in request to be tokenized
                            logger.debug("segment " + componentArr.getString(0));
                            if(requestedElements.getString(Constants.EDIFACT_SEGMENT_NAME).equalsIgnoreCase(componentArr.getString(0))) {
                                // and If segment name is equal
                                if(requestedElements.has(Constants.EDIFACT_SEGMENT_QUALIFIER)){
                                    if(requestedElements.getString(Constants.EDIFACT_SEGMENT_QUALIFIER).equalsIgnoreCase(componentArr.getString(1)))
                                    {
                                        if (requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_NUMBER) == (j + 1) //if composite element number is matched
                                                && requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_POSITION) == (k + 1)) {//and if element position is matched
                                            logger.debug("Segment found to be tokenized : " + requestedElements.getString(Constants.EDIFACT_SEGMENT_NAME));
                                            edifactTokenization(dataElementArray, k, method, senderId, receiverIds, requestedElements);
                                        }
                                    }
                                }else{
                                    if (requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_NUMBER) == (j + 1) //if composite element number is matched
                                            && requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_POSITION) == (k + 1)) {//and if element position is matched
                                       logger.debug("Segment found to be tokenized : " + requestedElements.getString(Constants.EDIFACT_SEGMENT_NAME));
                                        edifactTokenization(dataElementArray, k, method, senderId, receiverIds, requestedElements);
                                    }
                                }

                            } else{
                                //if needed to handle it later
                            }
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
            ///collect back the response to create the EDIFACT message back.
            response = response + EDIFACT_SEGMENT_TERMINATOR;
        }

        //Fix for UNA segment malformation issue https://github.com/kkavindra/ediTokenizer/issues/3
        if(response.indexOf("UNA") >= 0 ) {
            response = "UNA" +
                    EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR +
                    EDIFACT_DATA_ELEMENT_SEPERATOR +
                    EDIFACT_DECIMAL_SEPERATOR +
                    "? " + EDIFACT_SEGMENT_TERMINATOR +
                    response.substring(response.indexOf("UNB"));
            logger.debug(response);
        }

        logger.debug("Tokenized/Detokenized message " + response);
        return response;
    }

    private void edifactTokenization(JSONArray dataElementArray,int k,String method,String senderId,ArrayList receiverIds,JSONObject requestedElements) throws JSONException {
        //Create temproary JSON object to handle the current content.
        JSONObject jsonObjTemp = new JSONObject();
        logger.debug("Element to be handled :" + dataElementArray.get(k));
        jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,dataElementArray.get(k));
        //initialize cache object item with the data to be written
        if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_TOKENIZE)) {
            logger.debug("Tokenization selected for element");
            jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,tokenizer.tokenize(dataElementArray.get(k).toString(),requestedElements.getInt(Constants.EDIFACT_DATA_ELEMENT_LENGTH),senderId,receiverIds)) ;
        }else if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_DETOKENIZE)) {
            //get hte key to be retrived from current message.
            logger.debug("De-Tokenization selected for element for element " + dataElementArray.get(k).toString());
            CacheEntryObject tmpCacheEntryObject = null;
            //Retrieve the cache entry object from the cache.
            tmpCacheEntryObject = tokenizer.deTokenize(dataElementArray.get(k).toString(),senderId);

            //Retrieve the values from the object stored in the cache object.
            if(tmpCacheEntryObject.isErrorExists()) {
                logger.debug("No de-tokenized element returned from the data store");
                jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME, dataElementArray.get(k).toString());
            }else {
                logger.debug("Detokenized element returned from data store : " + tmpCacheEntryObject.getJsonObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME));
                jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME, tmpCacheEntryObject.getJsonObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME));
            }

        }else{
            //DO nothing
        }
        //put back the retrieved values from the cached object/key received from tokenization to element position back.
        dataElementArray.put(k,jsonObjTemp.get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME));
    }

    //method to seperate elemnts in edifact segments,composite elements given the separator.
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

