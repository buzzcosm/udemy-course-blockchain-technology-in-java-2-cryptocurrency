package com.buzzcosm.cryptocurrency.cryptocurrency;

import com.buzzcosm.cryptocurrency.blockchain.Block;
import com.buzzcosm.cryptocurrency.blockchain.Blockchain;
import com.buzzcosm.cryptocurrency.constants.Constants;

public class Miner {

    private double reward;

    public void mine(Block block, Blockchain blockchain) {

        // it takes some time to find the valid hash
        // This is a proof of work (PoW)
        while (!isGoldenHash(block)) {
            block.incrementNonce();
            block.generateHash();
        }

        System.out.println(block + " has just mined...");
        System.out.println("Hash is: " + block.getHash());

        blockchain.addBlock(block);
        reward += Constants.REWARD;

    }

    private boolean isGoldenHash(Block block) {
        String leadingZeros = new String(new char[Constants.DIFFICULTY]).replace('\0', '0');
        return block.getHash().substring(0, Constants.DIFFICULTY).equals(leadingZeros);
    }

    public double getReward() {
        return reward;
    }
}
