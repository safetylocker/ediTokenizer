package com.securitybox.storage;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class CacheEntryObject<receiverIds> implements Serializable {
    String senderId;
    ArrayList<receiverIds> receiverIds;
    ArrayList<AccessEntry> accessEntries;
    JSONObject jsonObject;

    public CacheEntryObject(){
        tokenCretionTime = new Date();
        accessEntries = new ArrayList();

    }

    public CacheEntryObject(String senderId,ArrayList<receiverIds> receiverIds,JSONObject jsonObject){
        tokenCretionTime = new Date();
        accessEntries = new ArrayList();
        this.senderId=senderId;
        this.jsonObject =jsonObject;

    }

    public Date tokenCretionTime;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
    public String getSenderIds() {
        return senderId;
    }

    public  void setSenderId(String senderId){
        this.senderId = senderId;
    }

    public String getSenderId(){
        return senderId;
    }
    public ArrayList<receiverIds> getReceiverIds() {
        return receiverIds;
    }
    public void setReceiverIds(ArrayList<receiverIds> receiverIds) {
        this.receiverIds = receiverIds;
    }
    public ArrayList<AccessEntry> getAccessLogs(){
        return accessEntries;
    }

}

