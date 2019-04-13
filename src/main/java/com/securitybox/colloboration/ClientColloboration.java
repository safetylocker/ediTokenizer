package com.securitybox.colloboration;

import com.securitybox.storage.CacheEntryObject;

public class ClientColloboration {
    CacheEntryObject cacheEntryObject;
    final static String ALLOW_DELETE="DELETE";
    final static String ALLOW_READ="READ";
    final static String ALLOW_UPDATE="UPDATE";
    final static String ALLOW_CREATE="CREATE";

    public ClientColloboration(CacheEntryObject cacheEntryObject) {
        this.cacheEntryObject = cacheEntryObject;
    }

    public boolean isAllowed(String clientId,String operation){
        //operation is for future use, where sender is able to specify what operation is a receiver allowed to do.
        //Which can be implemeted in cache entry object level based on receivers input
        for(String receiver:cacheEntryObject.getReceiverIds()){
            if(receiver.equalsIgnoreCase(clientId)){
                return true;
            }
        }
        return false;
    }

    public boolean isAllowed(String clientId){

        //if the client is sender, return grant the access
        if(cacheEntryObject.getSenderId().equalsIgnoreCase(clientId))
            return true;

        //if the client is in sender's list, grant the access
        for(String receiver:cacheEntryObject.getReceiverIds()){
            if(receiver.equalsIgnoreCase(clientId)){
                return true;
            }
        }

        return false;
    }
}
