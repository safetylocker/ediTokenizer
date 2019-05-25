package com.securitybox.tokenizer;

import com.securitybox.constants.Constants;
import com.securitybox.models.AccessEntry;
import com.securitybox.models.CacheEntryObject;
import com.securitybox.storage.DataStore;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class Tokenizer implements TokenizerDao {
  public static MessageDigest md;
  public Checksum checksum ;
  public static DataStore dataStore;
  final Logger logger = Logger.getLogger(this.getClass().getName());

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


    //method for tokenize a cache entry  object given the cache entry object and value
    @Override
    @Deprecated
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

    //method for tokenize a cache entry  given a value
    public String tokenize(String valueToTokenize, int length, String senderId, ArrayList<String> receiverIds) {
        try {
            String token=tokenize(valueToTokenize,length);
            logger.debug("Current token created/value/requested length-" + token+"/"+valueToTokenize+"/"+length);
            if(dataStore.storeValue(valueToTokenize,token,senderId,receiverIds))
                return token;
            else
                return Constants.DATA_STORE_ACTION_CREATTION_FAILURE;
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.DATA_STORE_ACTION_CREATTION_FAILURE;
        }
    }

    //method fro removing token entry.
    @Override
    public boolean removeTokenEntry(String key, String clientId) {

        return dataStore.removeTokenEntry(key, clientId);
    }

    public CacheEntryObject deTokenize(String key, String clientId) {
        logger.debug("Current key to detokenize a token " + key);
        if(key=="" || key==null) {
            CacheEntryObject tempCacheEntryObject_1 = new CacheEntryObject();
            tempCacheEntryObject_1.addErrorEntry(new AccessEntry(new Date(),clientId,Constants.ERROR_TOKEN_RETRIEVE));
            return tempCacheEntryObject_1;
        }else {
            return dataStore.retrieveObject(key, clientId);
        }
    }

    //method fro removing a token entry completely from the storage.
    @Override
    public boolean removeToken(String key){

      return dataStore.removeToken(key);
    }

    //retrieves access/audit logs from a cache entry object related to token.
    public ArrayList<AccessEntry> getAccessLogs(String key,String clientId){
        return dataStore.retrieveLogs(key,clientId).getAccessLogs();
    }


}
