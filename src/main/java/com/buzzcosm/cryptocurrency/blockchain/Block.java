package com.buzzcosm.cryptocurrency.blockchain;

import com.buzzcosm.cryptocurrency.constants.Constants;
import com.buzzcosm.cryptocurrency.cryptocurrency.CryptographyHelper;
import com.buzzcosm.cryptocurrency.cryptocurrency.Transaction;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
public class Block {

    private final String id;
    private final Long timestamp;

    private int nonce;

    @Getter
    @Setter
    private String hash;

    @Getter
    @Setter
    private String previousHash;

    @Getter
    private final List<Transaction> transactions;

    public Block(String previousHash) {
        this.id = UUID.randomUUID().toString();

        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.transactions = new ArrayList<>();
        generateHash();
    }

    public void generateHash() {
        String dataToHash = id +
                previousHash +
                timestamp +
                nonce +
                transactions;
        hash = CryptographyHelper.generateHash(dataToHash);
    }

    // the nonce is the only parameter the miner can tune (change) to find the valid hash
    public void incrementNonce() {
        this.nonce++;
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction == null) {
            return false;
        }

        // if the block is the genesis block we do not process the transaction
        if (!previousHash.equals(Constants.GENESIS_PREV_HASH)) {
            if (!transaction.verifyTransaction()) {
                log.error("Invalid transaction because of invalid signature...");
                return false;
            }
        }

        transactions.add(transaction);
        log.info("Transaction is valid and it's added to the block {}", this);
        return true;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", nonce=" + nonce +
                ", hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
