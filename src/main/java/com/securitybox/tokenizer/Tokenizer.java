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
    private String tokenize(String input,int keyLength) {
        String token;

        try {
            if(0 <=keyLength &&  keyLength <= 32) {
                md = MessageDigest.getInstance("MD5");
            }
            else if(33 <=keyLength && keyLength <= 40) {
                md = MessageDigest.getInstance("SHA-1");
            }
            else if(40 <=keyLength && keyLength <= 64){
                md = MessageDigest.getInstance("SHA-256");
            }
            else if(64 <=keyLength && keyLength <= 128){
                md = MessageDigest.getInstance("SHA-512");
            }else{
                md = MessageDigest.getInstance("MD2");
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
