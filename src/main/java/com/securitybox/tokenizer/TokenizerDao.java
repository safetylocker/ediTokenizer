package com.securitybox.tokenizer;

import com.securitybox.storage.DataStore;

import java.security.MessageDigest;

public interface TokenizerDao {
    MessageDigest md = null;
    DataStore dataStore=null;
}
