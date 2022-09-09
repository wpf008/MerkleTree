package com.kobe;

import java.util.List;

public interface MerkelTree {

    /**
     * @return rootHash
     */
    String getRoot();


    /**
     *
     * @param leaf
     * @return
     */
    List<String> getProof(String leaf);


    /**
     * hash function
     * @param node
     * @return
     */
    String hash(String node);


}
