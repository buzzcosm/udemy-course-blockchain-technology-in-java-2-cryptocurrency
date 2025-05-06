package com.buzzcosm.cryptocurrency.cryptocurrency;

import com.buzzcosm.cryptocurrency.blockchain.Block;
import com.buzzcosm.cryptocurrency.blockchain.Blockchain;
import com.buzzcosm.cryptocurrency.constants.Constants;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class Miner {

    // every miner gets 6.25 BTC after the mining operation
    private double reward;

    public void mine(Block block, Blockchain blockchain) {
        // it takes some time to find the valid hash
        // This is a proof of work (PoW)
        while (!isGoldenHash(block)) {
            block.incrementNonce();
            block.generateHash();
        }

        log.info( "{} has just mined...", block);
        log.info("Hash is: {}", block.getHash());

        blockchain.addBlock(block);
        reward += Constants.REWARD;
    }

    private boolean isGoldenHash(Block block) {
        String leadingZeros = new String(new char[Constants.DIFFICULTY]).replace('\0', '0');
        return block.getHash().substring(0, Constants.DIFFICULTY).equals(leadingZeros);
    }

}
