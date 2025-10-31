package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.models.InputData;
import org.example.models.AlgorithmResult;
import org.example.algorithms.PrimsAlgorithm;
import org.example.algorithms.KruskalsAlgorithm;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== MST Transportation Network Optimization ===");

        try {
            // Create Gson instance with pretty printing
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Read and parse input JSON file
            String inputPath = Paths.get("C:\\Users\\kusta\\OneDrive\\Рабочий стол\\Java.OOP\\DAA_A6_IK\\src\\test\\resources\\ass_3_input.json").toAbsolutePath().toString();
            System.out.println("Reading input file: " + inputPath);

            InputData inputData = gson.fromJson(new FileReader(inputPath), InputData.class);

            // Display basic information about parsed data
            if (inputData != null && inputData.getGraphs() != null) {
                System.out.println("✅ Successfully parsed input data!");
                System.out.println("Number of graphs: " + inputData.getGraphs().size());

                // Test both algorithms on each graph
                for (int i = 0; i < inputData.getGraphs().size(); i++) {
                    var graph = inputData.getGraphs().get(i);
                    System.out.println("\n" + "=".repeat(60));
                    System.out.println("--- Testing Graph " + graph.getId() + " ---");
                    System.out.println("Nodes: " + graph.getNodes().size() + " - " + graph.getNodes());
                    System.out.println("Edges: " + graph.getEdges().size());

                    // Run Prim's algorithm
                    System.out.println("\n Running Prim's Algorithm...");
                    var primResult = PrimsAlgorithm.findMST(graph);
                    displayAlgorithmResults("Prim", primResult);

                    // Run Kruskal's algorithm
                    System.out.println("\n Running Kruskal's Algorithm...");
                    var kruskalResult = KruskalsAlgorithm.findMST(graph);
                    displayAlgorithmResults("Kruskal", kruskalResult);

                    // Compare results
                    System.out.println("\n COMPARISON RESULTS:");
                    compareAlgorithms(primResult, kruskalResult);

                    // Verify against expected results
                    if (graph.getId() == 1) {
                        verifyGraph1Results(primResult, kruskalResult);
                    } else if (graph.getId() == 2) {
                        verifyGraph2Results(primResult, kruskalResult);
                    }
                }
            } else {
                System.out.println("❌ No graphs found in input file!");
            }

        } catch (IOException e) {
            System.err.println("❌ Error reading input file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Display results for a single algorithm
     */
    private static void displayAlgorithmResults(String algorithmName, AlgorithmResult result) {
        System.out.println("✅ " + algorithmName + " MST found!");
        System.out.println("-- Total cost: " + result.getTotal_cost());
        System.out.println("-- Operations count: " + result.getOperations_count());
        System.out.println("-- Execution time: " + String.format("%.2f", result.getExecution_time_ms()) + " ms");
        System.out.println("-- MST edges (" + result.getMst_edges().size() + "):");

        for (var edge : result.getMst_edges()) {
            System.out.println("   " + edge.getFrom() + " --(" + edge.getWeight() + ")--> " + edge.getTo());
        }
    }

    /**
     * Compare results of both algorithms
     */
    private static void compareAlgorithms(AlgorithmResult prim, AlgorithmResult kruskal) {
        boolean costsMatch = prim.getTotal_cost() == kruskal.getTotal_cost();
        boolean edgesCountMatch = prim.getMst_edges().size() == kruskal.getMst_edges().size();

        System.out.println("-- Total cost match: " + (costsMatch ? "✅ YES" : "❌ NO"));
        System.out.println("-- MST edges count match: " + (edgesCountMatch ? "✅ YES" : "❌ NO"));
        System.out.println("-- Prim operations: " + prim.getOperations_count() +
                " vs Kruskal operations: " + kruskal.getOperations_count());
        System.out.println("-- Prim time: " + String.format("%.2f", prim.getExecution_time_ms()) + " ms" +
                " vs Kruskal time: " + String.format("%.2f", kruskal.getExecution_time_ms()) + " ms");

        if (prim.getOperations_count() < kruskal.getOperations_count()) {
            System.out.println("-- Prim used fewer operations");
        } else if (prim.getOperations_count() > kruskal.getOperations_count()) {
            System.out.println("-- Kruskal used fewer operations");
        } else {
            System.out.println("-- Both algorithms used same number of operations");
        }
    }

    /**
     * Verify Graph 1 results against expected values
     */
    private static void verifyGraph1Results(AlgorithmResult primResult, AlgorithmResult kruskalResult) {
        System.out.println("\n--- Verifying Graph 1 results against expected values... ---");

        int expectedCost = 16;
        int expectedEdges = 4;

        verifyAlgorithmResults("Prim", primResult, expectedCost, expectedEdges);
        verifyAlgorithmResults("Kruskal", kruskalResult, expectedCost, expectedEdges);

        // Check if both algorithms produced same cost
        if (primResult.getTotal_cost() == kruskalResult.getTotal_cost()) {
            System.out.println("✅ Both algorithms produced identical total cost: " + primResult.getTotal_cost());
        } else {
            System.out.println("❌ Algorithms produced different costs!");
        }
    }

    /**
     * Verify Graph 2 results against expected values
     */
    private static void verifyGraph2Results(AlgorithmResult primResult, AlgorithmResult kruskalResult) {
        System.out.println("\n--- Verifying Graph 2 results against expected values... ---");

        int expectedCost = 6;
        int expectedEdges = 3;

        verifyAlgorithmResults("Prim", primResult, expectedCost, expectedEdges);
        verifyAlgorithmResults("Kruskal", kruskalResult, expectedCost, expectedEdges);
    }

    /**
     * Verify results for a single algorithm
     */
    private static void verifyAlgorithmResults(String algorithmName, AlgorithmResult result,
                                               int expectedCost, int expectedEdges) {
        boolean costOk = result.getTotal_cost() == expectedCost;
        boolean edgesOk = result.getMst_edges().size() == expectedEdges;

        System.out.println(algorithmName + ":");
        System.out.println("  Cost: " + result.getTotal_cost() + "/" + expectedCost + " " +
                (costOk ? "✅" : "❌"));
        System.out.println("  Edges: " + result.getMst_edges().size() + "/" + expectedEdges + " " +
                (edgesOk ? "✅" : "❌"));
    }
}