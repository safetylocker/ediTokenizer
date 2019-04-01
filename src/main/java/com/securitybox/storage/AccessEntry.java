package com.securitybox.storage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccessEntry{
    private Date acccesTime;
    private String clientId;
    private SimpleDateFormat simpleDateFormat;

    public AccessEntry(Date accessTime,String clientId){
        this.clientId=clientId;
        this.acccesTime = accessTime;

    }
    public Date getAcccesTime() {
        return acccesTime;
    }

    public void setAcccesTime(Date accessTime) {
        this.acccesTime = accessTime;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String toString(){
        return "cleintId:" +  clientId + "," + "Access time:"+acccesTime;
    }
}
