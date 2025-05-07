package com.buzzcosm.cryptocurrency.cryptocurrency;

import com.buzzcosm.cryptocurrency.blockchain.Blockchain;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Transaction {

    /**
     * id of the transaction is a hash (SHA-256) of the transaction data
     */
    @Getter
    @Setter
    private String transactionId;

    @Getter
    @Setter
    private PublicKey sender;

    @Getter
    @Setter
    private PublicKey receiver;

    /**
     * amount of coins the transaction sends to the receiver from the sender
     */
    @Getter
    @Setter
    private double amount;

    /**
     * Make sure that the transaction is signed by the sender
     * to prevent anyone else from spending the coins.
     */
    @Getter
    @Setter
    private byte[] signature;

    @Getter
    @Setter
    public List<TransactionInput> inputs;

    @Getter
    @Setter
    public List<TransactionOutput> outputs;

    public Transaction(PublicKey sender,
                       PublicKey receiver,
                       double amount,
                       List<TransactionInput> inputs) {

        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();

        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.inputs = inputs;

        calculateHash();
    }

    public boolean verifyTransaction() {

        if (!verifySignature()) {
            log.error("Invalid transaction because of invalid signature...");
            return false;
        }

        // let's gather unspent transactions (we have to consider inputs)
        for (TransactionInput transactionInput : inputs) {
            transactionInput.setUTXO(Blockchain.UTXOs.get(transactionInput.getTransactionOutputId()));
        }

        // Transactions have 2 parts: send an amount to the receiver + send the (balance - amount) back to the sender
        // Send value to the recipient
        outputs.add(new TransactionOutput(receiver, amount, transactionId));

        // Send the left over `change` back to the sender
        outputs.add(new TransactionOutput(sender, getInputsSum() - amount, transactionId));

        // WE HAVE TO UPDATE THE UTXOs
        // The outputs will be inputs for other transactions (so put them in the blockchain's UTXOs)
        for (TransactionOutput transactionOutput : outputs) {
            Blockchain.UTXOs.put(transactionOutput.getId(), transactionOutput);
        }

        // Remove transaction inputs from blockchain's UTXOs list because they've been spent
        for (TransactionInput transactionInput : inputs) {
            if (transactionInput.getUTXO() != null) {
                Blockchain.UTXOs.remove(transactionInput.getUTXO().getId());
            }
        }

        return true;
    }

    /**
     * This is how we calculate that how much money the sender has.
     * We have to consider transactions in the past.
     *
     * @return double as sum of all inputs
     */
    private double getInputsSum() {
        double sum = 0;

        for (TransactionInput transactionInput : inputs) {
            if (transactionInput.getUTXO() != null) {
                sum += transactionInput.getUTXO().getAmount();
            }
        }

        return sum;
    }

    public void generateSignature(PrivateKey privateKey) {
        String dataToSign = sender.toString() + receiver.toString() + amount;
        this.signature = CryptographyHelper.sign(privateKey, dataToSign);
    }

    public boolean verifySignature() {
        String dataToVerify = sender.toString() + receiver.toString() + amount;
        return CryptographyHelper.verify(sender, dataToVerify, signature);
    }

    private void calculateHash() {
        String hashData = sender.toString() + receiver.toString() + amount;
        this.transactionId = CryptographyHelper.generateHash(hashData);
    }
}
