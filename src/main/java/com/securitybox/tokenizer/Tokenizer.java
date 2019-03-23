package com.securitybox.tokenizer;

import com.securitybox.storage.CacheEntryObject;
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

    @Override
    public String deTokenize(Integer token) {
        return dataStore.retrieveValue(token);
    }


    //return the key of the object used to cache if caching is successfull
    //else return -1 to indicate it failes, thus it needs to be handled by the calling object
    @Override
    public int tokenize(CacheEntryObject cacheEntryObject) {
          System.out.println("current hash value " + cacheEntryObject.hashCode());
           if(dataStore.storeValue(cacheEntryObject.hashCode(), cacheEntryObject))
               return cacheEntryObject.hashCode();
           else
               return -1;
    }

    @Override
    public CacheEntryObject detokenize(int key) {
       System.out.println("Current key to detokenize detokenize()" + key);
      return dataStore.retrieveObject(key);

    }
}
