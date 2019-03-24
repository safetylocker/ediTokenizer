package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class CSVTest {
        String csvFile="AAA:BBB:CCC:DDD\n" +
                "KKK:2222:EEE:3333\n";
        JSONArray jsonArray = new JSONArray();


    @Test

        public void testFile() throws JSONException, NoSuchAlgorithmException {
            CSV csv = new CSV("\n",":");
            jsonArray.put(new JSONObject().put(Constants.CSV_DATA_ELEMENT_POSITION,2));
            jsonArray.put(new JSONObject().put(Constants.CSV_DATA_ELEMENT_POSITION,5));

            System.out.println(
            csv.docuemntHandler(Constants.TOKENIZER_METHOD_DETOKENIZE,jsonArray,csv.docuemntHandler(Constants.TOKENIZER_METHOD_TOKENIZE,jsonArray,csvFile,null,null),null,null)
            );
        }

}