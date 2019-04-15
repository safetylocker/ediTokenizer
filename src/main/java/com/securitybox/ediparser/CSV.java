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


public class CSV extends EdiDocument {
    String recordDelimeter,fieldDelimeter;
    public Tokenizer tokenizer = new Tokenizer();

    public CSV(String recordDelimeter,String fieldDelimeter) {
        super(Constants.DOCUMENT_TYPE_CSV);
        this.recordDelimeter=recordDelimeter;
        this.fieldDelimeter=fieldDelimeter;
    }

    public String getRecordDelimeter() {
        return recordDelimeter;
    }

    public void setRecordDelimeter(String recordDelimeter) {
        this.recordDelimeter = recordDelimeter;
    }

    public String getFieldDelimeter() {
        return fieldDelimeter;
    }

    public void setFieldDelimeter(String fieldDelimeter) {
        this.fieldDelimeter = fieldDelimeter;
    }

    public CSV() {
        super(Constants.DOCUMENT_TYPE_CSV);
    }


    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, String senderId, ArrayList<String> receiverIds) throws JSONException, NoSuchAlgorithmException {
        String response="";
        //get line as objects
        JSONArray csvResponse = seperateElements(message,this.recordDelimeter);
        for(int i= 0 ; i < csvResponse.length(); i++ ) {
            CSVRecord csvRecord = new CSVRecord(fieldDelimeter,"",csvResponse.getString(i).toString());
            for(int j=0;j<csvRecord.getCount(); j++ ){
                for(int ja=0 ; ja < objectsToBeTokenized.length();ja++){
                    JSONObject requestedElement = objectsToBeTokenized.getJSONObject(ja);
                    if(requestedElement.getInt(Constants.CSV_DATA_ELEMENT_POSITION)== j){
                        //Create temproary JSON object to handle the current content.
                        JSONObject jsonObjTemp = new JSONObject();
                        jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,csvRecord.getField(j));
                        //initialize cashe object item with the data to be written
                        if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_TOKENIZE)) {
                            CacheEntryObject cacheEntryObject = new CacheEntryObject(senderId,receiverIds,jsonObjTemp);
                            //set the object to be included in the cacheEntryObject
                            cacheEntryObject.setJsonObject(jsonObjTemp);
                            //call tokernization service with cacheObject to be tokenized
                            if (requestedElement.has(Constants.CSV_DATA_ELEMENT_LENGTH)) {
                                jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME, tokenizer.tokenize(cacheEntryObject, csvRecord.getField(j), requestedElement.getInt(Constants.CSV_DATA_ELEMENT_LENGTH),senderId));
                            }else{
                                jsonObjTemp.put(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME,tokenizer.tokenize(cacheEntryObject, csvRecord.getField(j),0,senderId));
                            }

                        }else if(method.equalsIgnoreCase(Constants.TOKENIZER_METHOD_DETOKENIZE)) {
                            //get hte key to be retrived from current message
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
                        //put back the retrieved values from the cached object/key received from tokenization to element position back
                        csvRecord.setFiled(j,jsonObjTemp.get(Constants.IGNITE_DEFAULT_CACHE_OBJECT_STORE_NAME).toString());
                    }

                }
            }

            response = response +  csvRecord.getRecord() + recordDelimeter;

        }


        return response ;
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

