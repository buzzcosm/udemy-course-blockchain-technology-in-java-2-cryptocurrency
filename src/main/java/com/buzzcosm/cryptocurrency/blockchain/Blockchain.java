package com.buzzcosm.cryptocurrency.blockchain;

import com.buzzcosm.cryptocurrency.cryptocurrency.TransactionOutput;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Blockchain {

    // this is the public leger - everyone can access
    // all the blocks (previous transactions) on the blockchain
    public static List<Block> blockchain;

    // we store every unspent transactions on the blockchain
    public static Map<String, TransactionOutput> UTXOs;

    public Blockchain() {
        Blockchain.blockchain = new LinkedList<>();
        Blockchain.UTXOs = new HashMap<>();
    }

    public void addBlock(Block block) {
        Blockchain.blockchain.add(block);
    }

    public int size() {
        return Blockchain.blockchain.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Block block : Blockchain.blockchain) {
            result.append(block.toString()).append("\n");
        }

        return result.toString();
    }
}
