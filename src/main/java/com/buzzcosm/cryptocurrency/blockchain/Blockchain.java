package com.buzzcosm.cryptocurrency.blockchain;

import java.util.LinkedList;
import java.util.List;

public class Blockchain {

    // immutable ledger
    // we are no able to remove blocks
    private List<Block> blockchain;

    public Blockchain() {
        blockchain = new LinkedList<>();
    }

    public void addBlock(Block block) {
        blockchain.add(block);
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }

    public int getSize() {
        return blockchain.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Block block : blockchain) {
            result.append(block.toString()).append("\n");
        }
        return result.toString();
    }
}
