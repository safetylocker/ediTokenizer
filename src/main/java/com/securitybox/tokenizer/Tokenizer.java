package com.securitybox.tokenizer;

import com.securitybox.storage.DataStore;

import javax.transaction.TransactionRequiredException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tokenizer implements TokenizerDao {
  public static MessageDigest md;
  public static DataStore dataStore;
  public Tokenizer() {
      dataStore = new DataStore();
    }
    //tokenize a given string value, keyLenght is used to select the algorithm
    //such that returned token is fit into specified max limit by client
    public String tokenize(String input, int keyLenght) {
        String token;
        try {
            md = MessageDigest.getInstance("MD2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        token = no.toString(16);
        while (token.length() < 32) {
            token = "0" + token;
        }
        dataStore.storeValue(token,input);
        return token;
    }

    //deokenize a given token
    public String deTokenize(String token) {
        return dataStore.retrieveValue(token);
    }
}
