package com.securitybox.storage;
import com.securitybox.constants.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccessEntry{
    private String acccesTime;
    private String clientId;
    private SimpleDateFormat simpleDateFormat;
    private String action;

    public AccessEntry(Date accessTime,String clientId){
        this.clientId=clientId;
        simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        this.acccesTime = simpleDateFormat.format(accessTime);
    }

    public AccessEntry(Date accessTime,String clientId,String action){
        this.clientId=clientId;
        simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        this.acccesTime = simpleDateFormat.format(accessTime);
        this.action=action;
    }
    public String getAcccesTime() {
        return acccesTime;
    }

    public void setAcccesTime(Date accessTime) {
        this.acccesTime = simpleDateFormat.format(accessTime);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String toString(){
        return "Client Id : " +  clientId + "," + "Access time : "+acccesTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
