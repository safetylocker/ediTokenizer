package com.securitybox.models;
import com.securitybox.models.AccessEntry;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class CacheEntryObject implements Serializable {
    String senderId;
    ArrayList<String> receiverIds;
    public ArrayList<AccessEntry> accessEntries;
    private ArrayList<AccessEntry> errorEntries;
    private boolean isErrorExists=false;
    JSONObject jsonObject;

    public CacheEntryObject(){
        tokenCretionTime = new Date();
        accessEntries = new ArrayList<AccessEntry>();
        errorEntries = new ArrayList<AccessEntry>();
        receiverIds = new ArrayList<String>();
    }

    public CacheEntryObject(String senderId,ArrayList<String> receiverIds,JSONObject jsonObject){
        tokenCretionTime = new Date();
        accessEntries = new ArrayList();
        this.senderId=senderId;
        this.jsonObject =jsonObject;
        this.receiverIds = receiverIds;
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
    public ArrayList<String> getReceiverIds() {
        return receiverIds;
    }
    public void setReceiverIds(ArrayList<String> receiverIds) {
        this.receiverIds = receiverIds;
    }
    public ArrayList<AccessEntry> getAccessLogs(){
        return accessEntries;
    }

    public ArrayList<AccessEntry> getErrorEntries() {
        return errorEntries;
    }

    public void addErrorEntry(AccessEntry accessEntry){
        errorEntries.add(accessEntry);
        this.setErrorExists(true);
    }

    public void setErrorEntries(ArrayList<AccessEntry> errorEntries) {
        this.errorEntries = errorEntries;
    }

    public boolean isErrorExists() {
        return isErrorExists;
    }

    public void setErrorExists(boolean errorExists) {
        isErrorExists = errorExists;
    }
}

