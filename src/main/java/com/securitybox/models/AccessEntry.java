package com.securitybox.models;
import com.securitybox.constants.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccessEntry{
    //In order to record the acess time of the entry.
    private String acccesTime;
    //keeps the client id of the client relevant to operation.
    private String clientId;
    private SimpleDateFormat simpleDateFormat;
    private String action;

    //Constructor for creating access entry
    public AccessEntry(Date accessTime,String clientId){
        this.clientId=clientId;
        simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        this.acccesTime = simpleDateFormat.format(accessTime);
    }
    //Constructor for creating access entry
    public AccessEntry(Date accessTime,String clientId,String action){
        this.clientId=clientId;
        simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        this.acccesTime = simpleDateFormat.format(accessTime);
        this.action=action;
    }
    //returns the access time of the object instance.
    public String getAcccesTime() {
        return acccesTime;
    }
    //set the access time of the object instance.
    public void setAcccesTime(Date accessTime) {
        this.acccesTime = simpleDateFormat.format(accessTime);
    }

    //returns the client id of the access object instance.
    public String getClientId() {
        return clientId;
    }

    //set the client id to the access object instance.
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    //returns the representation of access entry in string format.
    public String toString(){
        return "Client Id : " +  clientId + "," + "Access time : "+acccesTime;
    }

    //return the action of the access entry object.
    public String getAction() {
        return action;
    }

    //set action of the access entry object instance.
    public void setAction(String action) {
        this.action = action;
    }
}
