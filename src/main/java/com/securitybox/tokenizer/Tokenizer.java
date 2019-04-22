package com.securitybox.tokenizer;

import com.securitybox.models.AccessEntry;
import com.securitybox.models.CacheEntryObject;
import com.securitybox.storage.DataStore;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class Tokenizer implements TokenizerDao {
  public static MessageDigest md;
  public Checksum checksum ;
  public static DataStore dataStore;

  public Tokenizer() {
      dataStore = new DataStore();
    }
    //tokenize a given string value, keyLenght is used to select the algorithm
    //such that returned token is fit into specified max limit by client
    public String tokenize(String input,int minTokenLenght) {
        String token="";
        input= UUID.randomUUID().toString() + input;
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

            }else if(minTokenLenght >= 16){
                byte bytes[] = input.getBytes();
                checksum = new Adler32();
                checksum.update(bytes,0,bytes.length);
                long lngChecksum = checksum.getValue();
                return  Long.toString(lngChecksum);

            }else{
                return UUID.randomUUID().toString();
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


    //tokenize based on input value not object type
    @Override
    public String tokenize(CacheEntryObject cacheEntryObject, String valueToTokenize, int lenght, String clientId) {
        try {
            String token=tokenize(valueToTokenize,lenght);
            if(dataStore.storeValue(token, cacheEntryObject,clientId))
             return token;
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean removeTokenEntry(String key, String clientId) {

        return dataStore.removeTokenEntry(key, clientId);
    }

    public CacheEntryObject deTokenize(String key, String clientId) {
        System.out.println("Current key to detokenize detokenize()" + key);
        if(key=="" || key==null) {
            return null;
        }else {
            return dataStore.retrieveObject(key, clientId);
        }
    }

    @Override
    public boolean removeToken(String key){

      return dataStore.removeToken(key);
    }

    //retrieves access/audit logs from a cache entry object related to token.
    public ArrayList<AccessEntry> getAccessLogs(String key,String clientId){
        return dataStore.retrieveLogs(key,clientId).getAccessLogs();
    }


}
