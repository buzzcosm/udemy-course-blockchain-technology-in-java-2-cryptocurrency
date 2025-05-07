package com.buzzcosm.cryptocurrency.cryptocurrency;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionInput {

    /**
     * Every input has an output.
     * This is the transactionId of the {@link TransactionOutput}
     */
    private String transactionOutputId;

    /**
     * This is the unspent transaction output
     */
    private TransactionOutput UTXO;

    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }
}
