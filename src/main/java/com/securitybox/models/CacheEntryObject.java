package com.securitybox.models;
import com.securitybox.models.AccessEntry;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class CacheEntryObject implements Serializable {
    //records the client ID of the tokenization requester.
    String senderId;
    //records the intended receiver of the token entry.
    ArrayList<String> receiverIds;
    //to record the access/audit log entry when ever token is used.
    public ArrayList<AccessEntry> accessEntries;
    //to record the error entries related to cache entry object.
    private ArrayList<AccessEntry> errorEntries;
    //to indicate if there were errors handling the cache entry object during any operation.
    private boolean isErrorExists=false;
    //json object to store the real data being tokenized.
    JSONObject jsonObject;
    //recrod the time of the token creation time.
    public Date tokenCretionTime;

    //constructor for the cache entry object
    public CacheEntryObject(){
        tokenCretionTime = new Date();
        accessEntries = new ArrayList<AccessEntry>();
        errorEntries = new ArrayList<AccessEntry>();
        receiverIds = new ArrayList<String>();
    }
    //constructor for the cache entry object
    public CacheEntryObject(String senderId,ArrayList<String> receiverIds,JSONObject jsonObject){
        tokenCretionTime = new Date();
        accessEntries = new ArrayList();
        this.senderId=senderId;
        this.jsonObject =jsonObject;
        this.receiverIds = receiverIds;
    }

    //return the json object of current object instance.
    public JSONObject getJsonObject() {
        return jsonObject;
    }
    //set a provided json object to the current object instance.
    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
    //set the token creator id
    public  void setSenderId(String senderId){
        this.senderId = senderId;
    }
    //retrieves the token creator id
    public String getSenderId(){
        return senderId;
    }
    //return an array list of receiver id's
    public ArrayList<String> getReceiverIds() {
        return receiverIds;
    }
    //retrieve the access logs of the cache entry object.
    public ArrayList<AccessEntry> getAccessLogs(){
        return accessEntries;
    }
    //retrieve the errors logs of the cache entry object.
    public ArrayList<AccessEntry> getErrorEntries() {
        return errorEntries;
    }
    //add error entry to cache entry object
    public void addErrorEntry(AccessEntry accessEntry){
        errorEntries.add(accessEntry);
        this.setErrorExists(true);
    }
    //set the error entries provides a list of error entries.
    public void setErrorEntries(ArrayList<AccessEntry> errorEntries) {
        this.errorEntries = errorEntries;
    }
    //to check if errors exists in current cache entry object instance.
    public boolean isErrorExists() {
        return isErrorExists;
    }
    //set status of the cache entry object to indicate errors occured.
    public void setErrorExists(boolean errorExists) {
        isErrorExists = errorExists;
    }
}

