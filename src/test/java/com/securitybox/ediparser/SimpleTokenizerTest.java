package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SimpleTokenizerTest {

    SimpleTokenizer simpleTokenizer = new SimpleTokenizer();
    String token= "",tokenToDetokenize="";
    String strToTokenized = "message to be toknized";
    final static Logger logger = Logger.getLogger(SimpleTokenizerTest.class);
    String senderId="ClientA";
    String receiverId="ClientB";
    ArrayList<String> receiverIds=new ArrayList<String>();

    public SimpleTokenizerTest() {
        receiverIds.add(receiverId);
    }



    @Test
    public void tokenizeSingleValue() throws JSONException, NoSuchAlgorithmException {
         token = simpleTokenizer.tokenizeSingleValue(Constants.TOKENIZER_METHOD_TOKENIZE,strToTokenized,senderId,receiverIds,78);
         assertNotEquals("139a0144b4c514408bd514b3ccbb8377a885b194721503dce05dba78a7a1c5bd",token);
    }

    @Test
    public void deTokenizeSingleValue() throws JSONException, NoSuchAlgorithmException {
        tokenToDetokenize = simpleTokenizer.tokenizeSingleValue(Constants.TOKENIZER_METHOD_TOKENIZE,strToTokenized,senderId,receiverIds,78);
        if(logger.isDebugEnabled())logger.debug("Token to be de-tokenized : " + tokenToDetokenize);
        assertEquals(strToTokenized,simpleTokenizer.deTokenizeSingleValue(Constants.TOKENIZER_METHOD_DETOKENIZE,tokenToDetokenize,senderId));
    }


}