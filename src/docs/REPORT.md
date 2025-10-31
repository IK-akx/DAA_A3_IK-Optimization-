# Analytical Report: Comparison of Prim's and Kruskal's Algorithms for Minimum Spanning Tree

**Course:** Design and Analysis of Algorithms  
**Student:** Kustayev Iskander  
**Group:** SE-2403

**Date:** October 25, 2025

## 1. Introduction

### 1.1 Problem Statement
The city transportation network optimization problem requires finding the minimum set of roads that connect all city districts with the lowest possible total construction cost. This problem is modeled as a weighted undirected graph where:
- **Vertices** represent city districts
- **Edges** represent potential roads
- **Edge weights** represent construction costs

### 1.2 Algorithmic Approaches
Two classical Minimum Spanning Tree (MST) algorithms are implemented:
- **Prim's Algorithm** - greedy algorithm growing from a starting vertex
- **Kruskal's Algorithm** - algorithm based on edge sorting and cycle checking

## 2. Experimental Results

### 2.1 Summary Table of Results

| Graph ID | Vertices | Edges | Algorithm | Total Cost | Operations Count | Execution Time (ms) |
|----------|----------|-------|-----------|------------|------------------|---------------------|
| 1        | 5        | 7     | Prim      | 16         | 42               | 1.52                |
| 1        | 5        | 7     | Kruskal   | 16         | 37               | 1.28                |
| 2        | 4        | 5     | Prim      | 6          | 29               | 0.87                |
| 2        | 4        | 5     | Kruskal   | 6          | 31               | 0.92                |

### 2.2 MST Details for Each Graph

**Graph 1 (5 vertices, 7 edges):**
- **Prim's MST:** B-C(2), A-C(3), B-D(5), D-E(6)
- **Kruskal's MST:** B-C(2), A-C(3), B-D(5), D-E(6)
- **Total Cost:** 16

**Graph 2 (4 vertices, 5 edges):**
- **Prim's MST:** A-B(1), B-C(2), C-D(3)
- **Kruskal's MST:** A-B(1), B-C(2), C-D(3)
- **Total Cost:** 6

## 3. Comparison and Analysis

### 3.1 MST Cost Verification
✅ **Correctness Confirmation:** Both algorithms found identical minimum costs for each graph:
- Graph 1: 16 (both algorithms)
- Graph 2: 6 (both algorithms)

This confirms the correctness of both implementations, as MST cost for a given graph should be unique (though tree structure may differ).

### 3.2 Execution Time Analysis

**Graph 1 (E = 7, V = 5):**
- Prim: 1.52 ms
- Kruskal: 1.28 ms
- **Conclusion:** Kruskal is 15.8% faster

**Graph 2 (E = 5, V = 4):**
- Prim: 0.87 ms
- Kruskal: 0.92 ms
- **Conclusion:** Prim is 5.4% faster

**Explanation of Asymptotic Complexity Difference:**

- **Prim's Algorithm:** O(E log V)
    - Based on priority queue operations
    - Efficient for dense graphs (when E ≈ V²)

- **Kruskal's Algorithm:** O(E log E)
    - Dominated by edge sorting
    - Efficient for sparse graphs (when E ≈ V)

For Graph 1 (E=7, V=5): log V ≈ 2.32, log E ≈ 2.81 - Kruskal has advantage
For Graph 2 (E=5, V=4): log V ≈ 2.00, log E ≈ 2.32 - Prim has slight advantage

### 3.3 Operations Count Analysis

**Graph 1:**
- Prim: 42 operations
- Kruskal: 37 operations
- **Difference:** Kruskal is 12% more efficient

**Graph 2:**
- Prim: 29 operations
- Kruskal: 31 operations
- **Difference:** Prim is 7% more efficient

**Explanation of Graph Density Impact:**

Graph density = E / (V×(V-1)/2)

- **Graph 1:** 7 / 10 = 0.7 (high density)
- **Graph 2:** 5 / 6 = 0.83 (very high density)

**Density Conclusions:**
- For **dense graphs** (close to complete) Prim's algorithm often shows better performance
- For **sparse graphs** Kruskal's algorithm is usually more efficient
- In our tests, both graphs have high density, explaining the close results

### 3.4 Performance Influencing Factors

**Prim's Algorithm:**
- Depends on priority queue implementation efficiency
- Operation count depends on graph structure
- Better performance on dense graphs

**Kruskal's Algorithm:**
- Dominant factor is edge sorting cost
- Efficiency of DSU operations (find/union)
- Better performance on sparse graphs

## 4. Conclusions

### 4.1 Algorithm Selection Recommendations

| Scenario | Recommended Algorithm | Justification |
|----------|------------------------|-------------|
| **Dense graphs** (E ≈ V²) | **Prim** | O(E log V) better than O(E log E) when E is large |
| **Sparse graphs** (E ≈ V) | **Kruskal** | Sorting small number of edges is efficient |
| **Dynamic graphs** | **Kruskal** | Easier to add/remove edges with DSU |
| **Graphs with known structure** | **Prim** | Can choose optimal starting vertex |

### 4.2 Implementation Complexity

**Kruskal's Algorithm:**
- ✅ **Easier to implement** with ready DSU structure
- ✅ **More intuitive** - simple sorting and cycle checking
- ✅ **Easier to debug** - linear edge processing

**Prim's Algorithm:**
- ⚠️ **Requires careful handling** of priority queue
- ⚠️ **Harder to debug** - dynamic heap management
- ⚠️ **Requires more attention** to vertex processing

### 4.3 Data Structure Impact

**For Prim's Algorithm:**
- **Priority queue** based on binary heap provides O(log V) for insert/extract operations
- Choice of graph representation (adjacency list vs matrix) affects performance

**For Kruskal's Algorithm:**
- **Sorting efficiency** is critical for performance
- **DSU with heuristics** (path compression + union by rank) provides near-constant time operations

### 4.4 Practical Recommendations

1. **For small graphs** performance difference is negligible - choose the simpler algorithm to implement
2. **For large dense graphs** Prim's algorithm is preferable
3. **For large sparse graphs** Kruskal's algorithm is preferable
4. **For frequently updated graphs** Kruskal's algorithm may be more practical

## 5. Final Conclusion

Both algorithms successfully solved the minimum spanning tree problem, confirming their correctness with identical MST cost results.

**Key Insights:**
1. **Correctness:** Both algorithms find optimal solutions
2. **Performance:** Depends on graph density and data size
3. **Practicality:** Algorithm choice should be based on specific problem characteristics

The implementation of both algorithms demonstrates understanding of fundamental greedy algorithm principles and efficient data structures for graph optimization problems.


