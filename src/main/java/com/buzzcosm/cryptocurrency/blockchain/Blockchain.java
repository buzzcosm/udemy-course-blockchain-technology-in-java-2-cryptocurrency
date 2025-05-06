package com.buzzcosm.cryptocurrency.blockchain;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Blockchain {

    // immutable ledger
    // we are no able to remove blocks
    private final List<Block> blockchain;

    public Blockchain() {
        blockchain = new LinkedList<>();
    }

    public void addBlock(Block block) {
        blockchain.add(block);
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
