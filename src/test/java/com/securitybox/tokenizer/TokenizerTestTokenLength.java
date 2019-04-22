package com.securitybox.tokenizer;

import junit.framework.TestCase;
import org.json.JSONObject;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TokenizerTestTokenLength {


    public static void main(String args[]){
        Tokenizer tokenizer = new Tokenizer();

        JSONObject jsonObjTemp = new JSONObject();
        try{
            TestCase.assertEquals(null,tokenizer.tokenize("value to tokenize",15));
            assertTrue(tokenizer.tokenize("value to tokenize",33).length() == 32);
            assertTrue(tokenizer.tokenize("value to tokenize",45).length() == 39);
            assertTrue(tokenizer.tokenize("value to tokenize",65).length() == 64);
            assertTrue(tokenizer.tokenize("value to tokenize",130).length() == 128);

        } catch(Exception e) {
            e.printStackTrace();
        }


    }

}