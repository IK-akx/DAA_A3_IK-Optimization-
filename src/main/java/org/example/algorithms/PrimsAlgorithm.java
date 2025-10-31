package org.example.algorithms;

import org.example.models.AlgorithmResult;
import org.example.models.Edge;
import org.example.models.Graph;

import java.util.*;

public class PrimsAlgorithm {

    /**
     * Finds MST using Prim's algorithm
     * @param graph input graph
     * @return AlgorithmResult containing MST edges, total cost, operations count and execution time
     */
    public static AlgorithmResult findMST(Graph graph) {
        long startTime = System.nanoTime();
        int operationsCount = 0;

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        // If graph is empty, return empty result
        if (graph.getNodes() == null || graph.getNodes().isEmpty()) {
            return new AlgorithmResult(mstEdges, totalCost, operationsCount, 0);
        }

        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

        // Start from first node
        String startNode = graph.getNodes().get(0);
        visited.add(startNode);
        operationsCount++; // Adding to visited

        // Add all edges from start node to heap
        for (Edge edge : graph.getEdges()) {
            operationsCount++; // Each edge check
            if (edge.getFrom().equals(startNode) || edge.getTo().equals(startNode)) {
                minHeap.offer(edge);
                operationsCount++; // Heap offer
            }
        }

        while (!minHeap.isEmpty() && visited.size() < graph.getNodes().size()) {
            operationsCount++; // While loop condition check

            Edge minEdge = minHeap.poll();
            operationsCount++; // Heap poll

            String nextNode = null;

            // Determine which node is not visited
            if (!visited.contains(minEdge.getFrom())) {
                nextNode = minEdge.getFrom();
            } else if (!visited.contains(minEdge.getTo())) {
                nextNode = minEdge.getTo();
            }

            operationsCount += 2;

            // If both visited, skip this edge
            if (nextNode == null) {
                continue;
            }

            // Add edge to MST
            mstEdges.add(minEdge);
            totalCost += minEdge.getWeight();
            visited.add(nextNode);
            operationsCount += 3; // For add to list, cost addition, and visited add

            // Add all edges from the new node to heap
            for (Edge edge : graph.getEdges()) {
                operationsCount++; // Each edge check

                boolean fromNewNode = edge.getFrom().equals(nextNode) && !visited.contains(edge.getTo());
                boolean toNewNode = edge.getTo().equals(nextNode) && !visited.contains(edge.getFrom());
                operationsCount += 2; // Two contains checks

                if (fromNewNode || toNewNode) {
                    minHeap.offer(edge);
                    operationsCount++; // Heap offer
                }
            }
        }

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        return new AlgorithmResult(mstEdges, totalCost, operationsCount, executionTimeMs);
    }
}
