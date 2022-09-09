package com.kobe;


import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.Hash;
import org.web3j.utils.Strings;

import java.util.List;

public class Sha3MerkelTree extends AbstractMerkelTree {


    public Sha3MerkelTree(List<String> leaves) {
        super(leaves);
    }

    public Sha3MerkelTree(List<String> leaves, boolean sort) {
        super(leaves, sort);
    }

    @Override
    public String hash(String nodeVal) {
        if (nodeVal.startsWith("Ox")) {
            nodeVal = nodeVal.substring(2);
        }

        byte[] decodeByte = Hex.decode(nodeVal);
        byte[] reshash = Hash.sha3(decodeByte);
        return new String(Hex.encode(reshash));
    }
}
