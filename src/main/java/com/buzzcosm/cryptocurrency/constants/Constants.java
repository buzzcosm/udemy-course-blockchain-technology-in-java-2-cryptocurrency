package com.buzzcosm.cryptocurrency.constants;

public final class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    // this is the number of leading zeros
    public static final int DIFFICULTY = 5;

    // this is the hash (SHA-256) of the genesis block (previous hash of the first block)
    public static final String GENESIS_PREV_HASH = "0000000000000000000000000000000000000000000000000000000000000000";

    // amount of BTCs the miners get after the mining operation
    public static final double REWARD = 6.25;
}
