package com.ben.hbrewrittengs.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum ShardName
{
    /*
     * LOREWORD will be used in the lore (description) of the shard, depending on the SHARDNAME:
     *    Shard's Name: Shard of <SHARDNAME>
     *    Shard's Lore: It feels...<LOREWORD>!
     *                  Go capture this shard by holding it
     *                  and right clicking the enchantment table!
     */
    PESTILENCE    ("Shard of Pestilence", "infectious"),
    REPUGNANCE    ("Shard of Repugnance", "revolting"),
    UGLINESS      ("Shard of Ugliness", "uglyyy"),
    MALCONTENT    ("Shard of Malcontent", "irritating"),
    DEATHBRINGING ("Shard of Deathbringing", "EPIC"),
    FURY          ("Shard of Fury", "angery"),
    UNEASE        ("Shard of Unease", "itchy"),
    SPITE         ("Shard of Spite", "vengeful"),
    STINKY        ("Shard of Stinky", "phishy");

    private static final List<ShardName> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private final String SHARDNAME;
    private final String LOREWORD;

    private ShardName(String SHARDNAME, String LOREWORD)
    {
        this.SHARDNAME = SHARDNAME;
        this.LOREWORD = LOREWORD;
    }

    public static ShardName random()
    {
        return VALUES.get(ThreadLocalRandom.current().nextInt(VALUES.size()));
    }

    public String getName()
    {
        return SHARDNAME;
    }

    public String getLoreWord()
    {
        return LOREWORD;
    }
}
