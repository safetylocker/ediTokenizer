package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import com.securitybox.models.CSVRecord;
import com.securitybox.models.CacheEntryObject;
import com.securitybox.tokenizer.Tokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;


public class CSV extends EdiDocument {
    public Tokenizer tokenizer = new Tokenizer();
    public CSV() {
        super(Constants.DOCUMENT_TYPE_CSV);
    }

    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, String senderId, ArrayList<String> receiverIds,String recordDelimeter,String fieldDelimeter) throws JSONException, NoSuchAlgorithmException {
        String response="";
        //get line as objects
        JSONArray csvResponse = seperateElements(message,recordDelimeter);
        for(int i= 0 ; i < csvResponse.length(); i++ ) {
            CSVRecord csvRecord = new CSVRecord(fieldDelimeter,"",csvResponse.getString(i).replaceAll(recordDelimeter,""));
            //Iterate through CVS file records.
            for(int j=0;j<csvRecord.getCount(); j++ ){
                for(int ja=0 ; ja < objectsToBeTokenized.length();ja++){
                    JSONObject requestedElement = objectsToBeTokenized.getJSONObject(ja);
                    if(requestedElement.getInt(Constants.CSV_DATA_ELEMENT_POSITION)== j){
                        //Create temproary JSON object to handle the current content.
                        JSONObject jsonObjTemp = new JSONObject();
                        jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,csvRecord.getField(j));
                        //initialize cache object item with the data to be written.
                        if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_TOKENIZE)) {
                            jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,tokenizer.tokenize(csvRecord.getField(j),requestedElement.getInt(Constants.CSV_DATA_ELEMENT_LENGTH),senderId,receiverIds)) ;
                        }else if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_DETOKENIZE)) {
                            //get hte key to be retrived from current message.
                            System.out.println("current key to be de-tokenized " + csvRecord.getField(j));
                            //Retrieve the cache entry object from the cache
                            CacheEntryObject tmpCacheEntryObject = tokenizer.deTokenize(csvRecord.getField(j),senderId);
                            //Retrieve the values from the object stored in the cache object.
                            if(tmpCacheEntryObject.isErrorExists()) {
                                jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME, csvRecord.getField(j));
                            }else {
                                jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME, tmpCacheEntryObject.getJsonObject().get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME));
                            }
                        }
                        //put back the retrieved values from the cached object/key received from tokenization to element position back.
                        csvRecord.setFiled(j,jsonObjTemp.get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());
                    }
                }
            }
            response = response +  csvRecord.getRecord() + recordDelimeter;
        }
        return response ;
    }

    //method to seperate elemnts from the EDIFACT segments, composite elements given the separator .
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
                response +=  jsonArray.get(i).toString();        }
        return jsonArray;
    }

    @Override
    public boolean removeToken(String key){
        return tokenizer.removeToken(key);
    }

    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, String senderId, ArrayList<String> receiverIds) throws JSONException, NoSuchAlgorithmException {
        return null;
    }
}

