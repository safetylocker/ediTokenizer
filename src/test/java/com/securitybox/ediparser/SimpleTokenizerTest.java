package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import org.json.JSONException;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class SimpleTokenizerTest {

    SimpleTokenizer simpleTokenizer = new SimpleTokenizer();
    String token= "",tokenToDetokenize="";
    String strToTokenized = "message to be toknized";
    @Test
    public void tokenizeSingleValue() throws JSONException, NoSuchAlgorithmException {
         token = simpleTokenizer.tokenizeSingleValue(Constants.TOKENIZER_METHOD_TOKENIZE,strToTokenized,null,null,78);
         System.out.println("Token received from tokenization request : " + token);
         assertEquals("64f4f7333ab4613e7100d09e6e7c7fb762817edc3ecc35a9145e6a22d54eec88d1de18ebebec7adaad170e84720d522178475bedf5caf6b52b1a68e8b3b83df",token);

    }

    @Test
    public void deTokenizeSingleValue() throws JSONException, NoSuchAlgorithmException {
        tokenToDetokenize = simpleTokenizer.tokenizeSingleValue(Constants.TOKENIZER_METHOD_TOKENIZE,strToTokenized,null,null,78);
        System.out.println("Token to be de-tokenized : " + tokenToDetokenize);
        assertEquals(strToTokenized,simpleTokenizer.deTokenizeSingleValue(Constants.TOKENIZER_METHOD_DETOKENIZE,tokenToDetokenize,null,null));

    }

}