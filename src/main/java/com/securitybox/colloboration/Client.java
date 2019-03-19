package com.securitybox.colloboration;

class Client {
    private String clientId;

    public Client(String clienUsertName){
        this.clientId = clienUsertName;

    }
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
