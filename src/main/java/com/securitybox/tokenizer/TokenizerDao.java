package com.securitybox.tokenizer;

import com.securitybox.storage.DataStore;

import java.security.MessageDigest;

public interface TokenizerDao {
    String tokenize(String input, int keyLenght);
    String deTokenize(String token);
}
