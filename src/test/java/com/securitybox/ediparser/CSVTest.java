package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CSVTest {
        String csvFile="AAA:BBB:CCC:DDD\n" + "KKK:2222:EEE:3333\n";
        String senderId="ClientA";
        String receiverId="ClientB";
        ArrayList<String> receiverIds=new ArrayList<String>();

        JSONArray jsonArray = new JSONArray();

    @Test
    public void testFile() throws JSONException, NoSuchAlgorithmException {
            CSV csv = new CSV("\n",":");
            JSONObject obj_1=new JSONObject();
            obj_1.put(Constants.CSV_DATA_ELEMENT_POSITION,2);
            //obj_1.put(Constants.CSV_DATA_ELEMENT_LENGTH,130);

            JSONObject obj_2=new JSONObject();
            obj_2.put(Constants.CSV_DATA_ELEMENT_POSITION,5);
            obj_2.put(Constants.CSV_DATA_ELEMENT_LENGTH,45);

            receiverIds.add(receiverId);

            jsonArray.put(obj_1);
            jsonArray.put(obj_2);
            assertEquals(csvFile,csv.docuemntHandler(Constants.TOKENIZER_METHOD_DETOKENIZE,jsonArray,csv.docuemntHandler(Constants.TOKENIZER_METHOD_TOKENIZE,jsonArray,csvFile,senderId,receiverIds),senderId,receiverIds));
    }

}