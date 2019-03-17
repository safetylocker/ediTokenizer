package com.securitybox.storage;

public class Client {
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Client(String clientId) {
        this.clientId = clientId;
    }

    String clientId;

}
