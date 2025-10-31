package org.example.algorithms;

import org.example.models.AlgorithmResult;
import org.example.models.Edge;
import org.example.models.Graph;

import java.util.*;

public class KruskalsAlgorithm {

    /**
     * Finds MST using Kruskal's algorithm
     * @param graph input graph
     * @return AlgorithmResult containing MST edges, total cost, operations count and execution time
     */
    public static AlgorithmResult findMST(Graph graph) {
        int operationsCount = 0;

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        // If graph is empty, return empty result
        if (graph.getNodes() == null || graph.getNodes().isEmpty()) {
            return new AlgorithmResult(mstEdges, totalCost, operationsCount, 0);
        }

        // Create node to index mapping
        Map<String, Integer> nodeIndexMap = new HashMap<>();
        List<String> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            nodeIndexMap.put(nodes.get(i), i);
            operationsCount++; // Map insertion
        }

        // Sort edges by weight
        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        sortedEdges.sort(Comparator.comparingInt(Edge::getWeight));

        // Count sorting operations (n log n cmp)
        int n = sortedEdges.size();
        operationsCount += (int) (n * Math.log(n) / Math.log(2));

        // Initialize DSU
        DSU dsu = new DSU(nodes.size());

        // Process edges in sorted order
        for (Edge edge : sortedEdges) {
            operationsCount++; // For each edge iteration

            int u = nodeIndexMap.get(edge.getFrom());
            int v = nodeIndexMap.get(edge.getTo());
            operationsCount += 2; // For two map lookups

            // Check if adding this edge creates a cycle
            if (dsu.union(u, v)) {
                // No cycle, add to MST
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                operationsCount += 2; // For add to list and cost addition
            }

            // Stop if we have enough edges (V-1 edges for V vertices)
            operationsCount++;
            if (mstEdges.size() == nodes.size() - 1) {
                break;
            }
        }

        // Add DSU operations to total operations count
        operationsCount += dsu.getOperationsCount();


        return new AlgorithmResult(mstEdges, totalCost, operationsCount, 0);
    }
}
