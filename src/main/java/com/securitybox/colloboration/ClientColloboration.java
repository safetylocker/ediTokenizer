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
        //Which can be implemented in cache entry object level based on receivers input
        for(String receiver:cacheEntryObject.getReceiverIds()){
            if(receiver.equalsIgnoreCase(clientId)){
                return true;
            }
        }
        return false;
    }

    public boolean isAllowed(String clientId){
        boolean returnVal=false;
        System.out.println("Check client id for access to object " + clientId);
        //if the client is sender, grant the access to the client.
        if(cacheEntryObject.getSenderId().equalsIgnoreCase(clientId)) {
            System.out.println("Sender ID " + cacheEntryObject.getSenderId() + " matched  with  " + clientId);
            returnVal = true;
        }

        //if the client ID is in sender's list, grant the access to the client.
        if(cacheEntryObject.getReceiverIds()!= null){
            for(String receiver:cacheEntryObject.getReceiverIds()){
                if(receiver.equalsIgnoreCase(clientId)){
                    System.out.println("Receiver ID " + receiver + " matched  with  " + clientId);
                    returnVal= true;
                    break;
                }
            }
        }


        return  returnVal;
    }
}
