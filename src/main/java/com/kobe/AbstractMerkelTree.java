package com.kobe;

import java.util.*;

public abstract class AbstractMerkelTree implements MerkelTree {

    private List<List<String>> tree;

    private String root;
    private List<String> leaves;


    private Map<String, String> nodePosition;

    private boolean sort;


    public AbstractMerkelTree(List<String> leaves) {
        this(leaves, false);
    }

    public AbstractMerkelTree(List<String> leaves, boolean sort) {
        this.leaves = leaves;
        this.tree = new ArrayList<>();
        root = "";
        this.sort = sort;
        initMerkleTree();
        nodePosition = new HashMap<>();
    }


    /**
     * build MerkleTree
     */
    public void initMerkleTree() {
        if (sort) {
            Collections.sort(this.leaves, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
        }
        List<String> newTxList = getNewTxList(this.leaves);
        while (newTxList.size() != 1) {
            newTxList = getNewTxList(newTxList);
        }
        this.root = newTxList.get(0);
        List<String> rootList = new ArrayList<>();
        rootList.add(root);
        tree.add(rootList);
    }


    @Override
    public String getRoot() {
        return this.root;
    }

    @Override
    public List<String> getProof(String leaf) {
        List<String> proofList = new ArrayList<>();
        getProof(leaf, 0, proofList);
        return proofList;
    }


    private List<String> getNewTxList(List<String> tempTxList) {
        tree.add(tempTxList);
        List<String> newTxList = new ArrayList<String>();
        int index = 0;
        while (index < tempTxList.size()) {
            String left = tempTxList.get(index); // left
            index++;
            String right = ""; // right
            if (index != tempTxList.size()) {
                right = tempTxList.get(index);
            }
            if ("".equals(right)) {
                newTxList.add(left);
            } else {
                if (sort) {
                    if (left.compareTo(right) > 0) {
                        String temp = left;
                        left = right;
                        right = temp;
                    }
                }
                String sha2HexValue = hash(left + right);
                newTxList.add(sha2HexValue);
            }
            index++;
        }
        return newTxList;
    }

    private void getProof(String leaf, int height, List<String> proofList) {
        if (height == tree.size()) //find root node
            return;
        List<String> nodes = tree.get(height);
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).equals(leaf)) {
                String parentHash = "";
                if (i % 2 == 0) {
                    if (i + 1 < nodes.size()) {
                        String right = nodes.get(i + 1);
                        proofList.add(right);
                        if (sort) {
                            if (leaf.compareTo(right) > 0) {
                                String temp = right;
                                right = leaf;
                                leaf = temp;
                            }
                        }
                        parentHash = hash(leaf + right);
                    } else {
                        parentHash = leaf;
                    }
                } else {
                    String left = nodes.get(i - 1);
                    proofList.add(left);
                    if (sort) {
                        if (left.compareTo(leaf) > 0) {
                            String temp = left;
                            left = leaf;
                            leaf = temp;
                        }
                    }
                    parentHash = hash(left + leaf);
                }
                getProof(parentHash, height + 1, proofList);
                break;
            }
        }
    }


    private boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }


    @Override
    public boolean verify(String leaf, List<String> proofs, String root) {
        assert !isEmpty(leaf) : "leaf required";
        assert !isEmpty(root) : "root required";
        assert proofs != null : "proofs required";
        if (sort) return verifyWithSorted(leaf, proofs, root);
        return verifyWithSorted(leaf, proofs, root);
    }


    /**
     * Unsorted MerkelTree verify proofs
     *
     * @param leaf
     * @param proofs
     * @param root
     * @return
     */
    private boolean verifyWithNoSorted(String leaf, List<String> proofs, String root) {

        return false;
    }


    /**
     * Sorted MerkelTree verify proofs
     *
     * @param leaf
     * @param proofs
     * @param root
     * @return
     */
    private boolean verifyWithSorted(String leaf, List<String> proofs, String root) {
        String computedHash = "";
        for (String proof : proofs) {
            if (leaf.compareTo(proof) >= 0) {
                computedHash = hash(proof + leaf);
            } else {
                computedHash = hash(leaf + proof);
            }
        }
        return root.equals(computedHash);
    }

}
