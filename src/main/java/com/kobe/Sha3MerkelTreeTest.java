package com.kobe;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.Hash;

import java.util.ArrayList;
import java.util.List;

public class Sha3MerkelTreeTest {

    public static void main(String[] args) {

        List<String> tempTxList = new ArrayList<String>();
        tempTxList.add("0x617E266FFA5c2B168fB6B6cE1Bee9CA2E461DD58");
        tempTxList.add("0x617E266FFA5c2B168fB6B6cE1Bee9CA2E461DD57");
        tempTxList.add("0x617E266FFA5c2B168fB6B6cE1Bee9CA2E461DD56");
        tempTxList.add("0x617E266FFA5c2B168fB6B6cE1Bee9CA2E461DD55");
        tempTxList.add("0x617E266FFA5c2B168fB6B6cE1Bee9CA2E461DD54");
        tempTxList.add("0x617E266FFA5c2B168fB6B6cE1Bee9CA2E461DD53");
        tempTxList.add("0x617E266FFA5c2B168fB6B6cE1Bee9CA2E461DD52");
        tempTxList.add("0x617E266FFA5c2B168fB6B6cE1Bee9CA2E461DD51");
        tempTxList.add("0x617E266FFA5c2B168fB6B6cE1Bee9CA2E461DD50");
        tempTxList.add("0xB302b1982494621c4a7B0F57723Ae14BDd101f12");
        tempTxList.add("0x9CB9e9165707db8a4e0076fB9D5641B613ea2884");
        tempTxList.add("0x98C5Bbd98cc48bcaD841C8DBebfDd0B1cfBF262B");
        tempTxList.add("0x21bA821BFF24E2f5532DDbbfA0f9ae58dCeEF17D");
        tempTxList.add("0x34C3FF1B290aafE3D959F5ceE78b9D566dbd1B08");
        tempTxList.add("0x0b1c8f78F0d8CE21e284b5Acd521f8d13DC0AC94");
        tempTxList.add("0x46a3A41bd932244Dd08186e4c19F1a7E48cbcDf4");
        tempTxList.add("0x34fDB4ec9543742ed3366BA99fE617f8db5a63Be");

        List<String> leaves = new ArrayList<>();
        for (String s : tempTxList) {
            leaves.add(hash(s));
        }
        Sha3MerkelTree merkleTrees = new Sha3MerkelTree(leaves, true);
        System.out.println("ROOT:  " + merkleTrees.getRoot());
        tempTxList.add("0x34fDB4ec9543742ed3366BA99fE617f8db5a63B4");//不在的节点验证
        for (String data : tempTxList) {
            String leaf = merkleTrees.hash(data);
            List<String> proof = merkleTrees.getProof(leaf);
            boolean verify = merkleTrees.verify(leaf, proof, merkleTrees.getRoot());
            System.out.println("data is : " + data + " proof is " + verify);
        }
    }

    public static String hash(String nodeVal) {
        if (nodeVal.startsWith("0x")) {
            nodeVal = nodeVal.substring(2);
        }
        byte[] decodeByte = Hex.decode(nodeVal);
        byte[] reshash = Hash.sha3(decodeByte);
        return new String(Hex.encode(reshash));
    }
}
