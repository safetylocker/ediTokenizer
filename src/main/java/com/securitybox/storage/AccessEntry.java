package com.securitybox.storage;

import java.util.Date;

public class AccessEntry{
    private Date acccesTime;
    private String clientId;

    public AccessEntry(Date acccesTime,String clientId){
        this.acccesTime=acccesTime;
        this.clientId=clientId;
    }
    public Date getAcccesTime() {
        return acccesTime;
    }

    public void setAcccesTime(Date acccesTime) {
        this.acccesTime = acccesTime;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
