package com.buzzcosm.cryptocurrency.blockchain;

import com.buzzcosm.cryptocurrency.cryptocurrency.CryptographyHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Block {

    private final int id;
    private final Long timestamp;
    private final String transaction;

    private int nonce;

    @Setter
    @Getter
    private String hash;

    @Setter
    @Getter
    private String previousHash;

    public Block(int id, String transaction, String previousHash) {
        this.id = id;
        this.transaction = transaction;
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        generateHash();
    }

    public void generateHash() {
        String dataToHash = id +
                previousHash +
                timestamp +
                nonce +
                transaction;
        hash = CryptographyHelper.generateHash(dataToHash);
    }

    public void incrementNonce() {
        this.nonce++;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", transaction='" + transaction + '\'' +
                '}';
    }
}
