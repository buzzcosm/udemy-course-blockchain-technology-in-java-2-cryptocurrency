package com.buzzcosm.cryptocurrency.cryptocurrency;

import com.buzzcosm.cryptocurrency.blockchain.Blockchain;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@Getter
public class Wallet {
    // users of the network
    // used for signature
    private final PrivateKey privateKey;
    // verification
    // address: (RIPE Message Digest) public key (160 bits)
    private final PublicKey publicKey;

    public Wallet() {
        KeyPair keyPair = CryptographyHelper.ellipticCurveCrypto();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    // WE ARE ABLE TO TRANSFER MONEY !!!
    // miners of the blockchain will put this transaction into the blockchain
    public Transaction transferMoney(PublicKey receiver, double amount) {

        if (calculateBalance() < amount) {
            log.error("Invalid transaction because of not enough money...");
            return null;
        }

        // we store the inputs for the transaction in this array
        List<TransactionInput> inputs = new ArrayList<>();

        // let's find out unspent transactions (we have to consider inputs)
        // the blockchain stores all the UTXOs
        for (Map.Entry<String, TransactionOutput> item : Blockchain.UTXOs.entrySet()) {
            TransactionOutput UTXO = item.getValue();

            if (UTXO.isMine(publicKey)) {
                inputs.add(new TransactionInput(UTXO.getId()));
            }
        }

        // let's create the new transaction
        Transaction newTransaction = new Transaction(publicKey, receiver, amount, inputs);

        // the sender signs the transaction
        newTransaction.generateSignature(privateKey);

        return newTransaction;
    }

    // there is no balance associated with the users
    // UTXOs (Unspent Transaction Outputs) and consider all the transactions in the past
    public double calculateBalance() {
        double balance = 0;
        for (Map.Entry<String, TransactionOutput> item : Blockchain.UTXOs.entrySet()) {
            TransactionOutput transactionOutput = item.getValue();

            if (transactionOutput.isMine(publicKey)) {
                balance += transactionOutput.getAmount();
            }

        }
        return balance;
    }
}
