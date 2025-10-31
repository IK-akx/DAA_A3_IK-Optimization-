package org.example.algorithms;

public class DSU {
    private int[] parent;
    private int[] rank;
    private int operationsCount;

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        operationsCount = 0;

        // Initialize each element as its own parent
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
            operationsCount++; // For initialization
        }
    }

    /**
     * Find with path compression
     * @param x element to find
     * @return root of the set containing x
     */
    public int find(int x) {
        operationsCount++; // For function call
        if (parent[x] != x) {
            operationsCount++; // For comparison
            parent[x] = find(parent[x]); // Path compression
            operationsCount++; // For assignment
        }
        return parent[x];
    }

    /**
     * Union by rank
     * @param x first element
     * @param y second element
     * @return true if union was performed, false if already in same set
     */
    public boolean union(int x, int y) {
        operationsCount++; // For function call

        int rootX = find(x);
        int rootY = find(y);

        operationsCount++; // For comparison
        if (rootX == rootY) {
            return false;
        }

        // Union by rank
        operationsCount++; // For comparison
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
            operationsCount++; // For assignment
        } else if (rank[rootX] > rank[rootY]) {
            operationsCount++; // For comparison
            parent[rootY] = rootX;
            operationsCount++; // For assignment
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
            operationsCount += 2; // For assignment and increment
        }

        return true;
    }

    public int getOperationsCount() {
        return operationsCount;
    }
}