package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import com.securitybox.tokenizer.Tokenizer;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.json.JSONArray;
import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CSV extends EdiDocument {
    public CSV(String docType) {
        super(Constants.DOCUMENT_TYPE_CSV);
    }
    String endofLine="\n";

    @Override
    public String docuemntHandler(String method, JSONArray objectToBeTokenized, String message) throws JSONException, NoSuchAlgorithmException {
        return null;
    }


    @Override
    public String docuemntHandler(String method, JSONArray objectsToBeTokenized, String message, ArrayList<String> senderIds, ArrayList<String> receiverIds) throws JSONException, NoSuchAlgorithmException {
        String response;
        JSONArray csvResponse = seperateElements(message,"\n");


        return null;
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

