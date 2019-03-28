package com.securitybox.storage;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public abstract class CacheEntryObject<senderIds, receiverIds> implements Serializable {

    ArrayList<senderIds> senderIds;
    ArrayList<receiverIds> receiverIds;
    JSONObject object;
    public Date tokenCretionTime,tokenValidityTime;

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }



    public ArrayList<senderIds> getSenderIds() {
        return senderIds;
    }

    public void setSenderIds(ArrayList<senderIds> senderIds) {
        this.senderIds = senderIds;
    }

    public ArrayList<receiverIds> getReceiverIds() {
        return receiverIds;
    }

    public void setReceiverIds(ArrayList<receiverIds> receiverIds) {
        this.receiverIds = receiverIds;
    }



}
