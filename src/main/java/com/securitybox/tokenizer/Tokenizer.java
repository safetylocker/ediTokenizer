package com.securitybox.tokenizer;

import com.securitybox.storage.CacheEntryObject;
import com.securitybox.storage.DataStore;
import org.json.JSONException;

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
    public String tokenize(String input,int minTokenLenght) {
        String token="";

        try {
            if(minTokenLenght >= 128) {
                md = MessageDigest.getInstance("SHA-512");
            }
            else if(minTokenLenght >= 64) {
                md = MessageDigest.getInstance("SHA-256");
            }
            else if(minTokenLenght >= 39) {
                md = MessageDigest.getInstance("SHA-1");
            }
            else if(minTokenLenght >= 32){
                md = MessageDigest.getInstance("MD5");
            }else{
                return null;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        token = no.toString(16);
        while (token.length() < 32) {
            token = "0" + token;
        }

        return token;
    }


    //Detokenize fucntion for integer type token
    public String deTokenize(Integer token) {
        return dataStore.retrieveValue(token);
    }

    //Detokenize fucntion for string type token
    public String deTokenize(String token) {
        return dataStore.retrieveValue(token);
    }


    //return the key of the object used to cache if caching is successfull
    //else return -1 to indicate it failes, thus it needs to be handled by the calling object
    public int tokenize(CacheEntryObject cacheEntryObject) {
           int tmpHashCode= cacheEntryObject.hashCode();
           if(dataStore.storeValue(tmpHashCode, cacheEntryObject))
               return tmpHashCode;
           else
               return -1;
    }

    //tokenize based on input value not object type
    public String tokenize(CacheEntryObject cacheEntryObject,String valueToTokenize,int lenght) {
        try {
            String token=tokenize(valueToTokenize,lenght);
            if(dataStore.storeValue(token, cacheEntryObject))
             return token;
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public CacheEntryObject detokenize(int key) {
        System.out.println("Current key to detokenize detokenize()" + key);
        return dataStore.retrieveObject(key);

    }

    public CacheEntryObject detokenize(String key) {
        System.out.println("Current key to detokenize detokenize()" + key);
        return dataStore.retrieveObject(key);

    }
}
