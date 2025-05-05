package com.buzzcosm.cryptocurrency.blockchain;

import com.buzzcosm.cryptocurrency.cryptocurrency.CryptographyHelper;

import java.util.Date;

public class Block {

    private int id;
    private int nonce;
    private Long timestamp;
    private String hash;
    private String previousHash;
    private String transaction;

    public Block(int id, String transaction, String previousHash) {
        this.id = id;
        this.transaction = transaction;
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        generateHash();
    }

    public void generateHash() {
        String dataToHash = Integer.toString(id) +
                previousHash +
                Long.toString(timestamp) +
                Integer.toString(nonce) +
                transaction;
        hash = CryptographyHelper.generateHash(dataToHash);
    }

    public void incrementNonce() {
        this.nonce++;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
