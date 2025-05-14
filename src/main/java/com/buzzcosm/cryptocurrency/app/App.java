package com.buzzcosm.cryptocurrency.app;

import com.buzzcosm.cryptocurrency.blockchain.Block;
import com.buzzcosm.cryptocurrency.blockchain.Blockchain;
import com.buzzcosm.cryptocurrency.constants.Constants;
import com.buzzcosm.cryptocurrency.cryptocurrency.*;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

@Log4j2
public class App {

    public static void main(String[] args) {

        // we use bouncy castle (BC) as the cryptography related provider
        Security.addProvider(new BouncyCastleProvider());

        // create wallets + blockchain + single miner in the network
        Wallet userA = new Wallet();
        Wallet userB = new Wallet();
        Wallet lender = new Wallet();
        Blockchain chain = new Blockchain();
        Miner miner = new Miner();

        // create genesis transaction that send 500 coins to userA
        Transaction genesisTransaction = new Transaction(
                lender.getPublicKey(), userA.getPublicKey(), 500, null);
        genesisTransaction.generateSignature(lender.getPrivateKey());
        genesisTransaction.setTransactionId("0");
        genesisTransaction.outputs.add(new TransactionOutput(
                genesisTransaction.getReceiver(),
                genesisTransaction.getAmount(),
                genesisTransaction.getTransactionId())
        );
        Blockchain.UTXOs.put(genesisTransaction.outputs.getFirst().getId(), genesisTransaction.outputs.getFirst());

        log.info("Constructing the genesis block...");
        Block genesis = new Block(Constants.GENESIS_PREV_HASH);
        genesis.addTransaction(genesisTransaction);
        miner.mine(genesis, chain);

        Block block1 = new Block(genesis.getHash());
        log.info("userA's balance is: {}", userA.calculateBalance());
        log.info("userA tries to send money (120 coins) to userB...");
        block1.addTransaction(userA.transferMoney(userB.getPublicKey(), 120));
        miner.mine(block1, chain);
        log.info("userA's balance is: {}", userA.calculateBalance());
        log.info("userB's balance is: {}", userB.calculateBalance());

        Block block2 = new Block(block1.getHash());
        log.info("userA send more funds (600) that it has...");
        block2.addTransaction(userA.transferMoney(userB.getPublicKey(), 600));
        miner.mine(block2, chain);
        log.info("userA's balance is: {}", userA.calculateBalance());
        log.info("userB's balance is: {}", userB.calculateBalance());

        Block block3 = new Block(block2.getHash());
        log.info("userB is attempting to send funds (110) to userA...");
        block3.addTransaction(userB.transferMoney(userA.getPublicKey(), 110));
        miner.mine(block3, chain);
        log.info("userA's balance is: {}", userA.calculateBalance());
        log.info("userB's balance is: {}", userB.calculateBalance());

        log.info("Miner's reward is: {}", miner.getReward());
    }
}
