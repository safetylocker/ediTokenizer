package com.securitybox.storage;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataStoreTest {

    @Test
    public void storeValue() {
        DataStore dataStore =  new DataStore();
        System.out.println(dataStore.storeValue("1","myvalue"));
        System.out.println(dataStore.retrieveValue("1"));
    }

    @Test
    public void retrieveValue() {
    }
}