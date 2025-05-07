package com.buzzcosm.cryptocurrency.cryptocurrency;

import lombok.Getter;
import lombok.Setter;

import java.security.PublicKey;

public class TransactionOutput {

    /**
     * Identifier of the transaction output (SHA-256 hash)
     */
    @Getter
    private String id;

    /**
     * Transaction id of the parent transaction (so the transaction it was created in)
     */
    @Getter
    @Setter
    private String parentTransactionId;

    /**
     * The new owner of the coin(s)
     */
    @Getter
    @Setter
    private PublicKey receiver;

    /**
     * The amount of coins
     */
    @Getter
    @Setter
    private double amount;

    public TransactionOutput(PublicKey receiver, double amount, String parentTransactionId) {
        this.receiver = receiver;
        this.amount = amount;
        this.parentTransactionId = parentTransactionId;
        generateId();
    }

    private void generateId() {
        this.id = CryptographyHelper.generateHash(receiver.toString() + amount + parentTransactionId);
    }

    public boolean isMine(PublicKey publicKey) {
        return publicKey.equals(receiver);
    }
}
