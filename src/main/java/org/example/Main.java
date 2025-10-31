package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.models.*;
import org.example.algorithms.PrimsAlgorithm;
import org.example.algorithms.KruskalsAlgorithm;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
                System.out.println(" Number of graphs: " + inputData.getGraphs().size());

                List<GraphResult> results = new ArrayList<>();

                // Process each graph
                for (var graph : inputData.getGraphs()) {
                    System.out.println("\n" + "=".repeat(60));
                    System.out.println("--- Processing Graph " + graph.getId() + " ---");
                    System.out.println("Nodes: " + graph.getNodes().size() + " - " + graph.getNodes());
                    System.out.println("Edges: " + graph.getEdges().size());

                    // Create input statistics
                    InputStats inputStats = new InputStats(
                            graph.getNodes().size(),
                            graph.getEdges().size()
                    );

                    // Run Prim's algorithm with precise timing
                    System.out.println("\n-- Running Prim's Algorithm...");
                    long primStartTime = System.nanoTime();
                    AlgorithmResult primResult = PrimsAlgorithm.findMST(graph);
                    long primEndTime = System.nanoTime();
                    double primTimeMs = (primEndTime - primStartTime) / 1_000_000.0;
                    primResult.setExecution_time_ms(primTimeMs);

                    // Run Kruskal's algorithm with precise timing
                    System.out.println("-- Running Kruskal's Algorithm...");
                    long kruskalStartTime = System.nanoTime();
                    AlgorithmResult kruskalResult = KruskalsAlgorithm.findMST(graph);
                    long kruskalEndTime = System.nanoTime();
                    double kruskalTimeMs = (kruskalEndTime - kruskalStartTime) / 1_000_000.0;
                    kruskalResult.setExecution_time_ms(kruskalTimeMs);

                    // Display results
                    displayAlgorithmResults("Prim", primResult);
                    displayAlgorithmResults("Kruskal", kruskalResult);

                    // Compare results
                    System.out.println("\n-- COMPARISON RESULTS:");
                    compareAlgorithms(primResult, kruskalResult);

                    // Create graph result
                    GraphResult graphResult = new GraphResult(
                            graph.getId(),
                            inputStats,
                            primResult,
                            kruskalResult
                    );

                    results.add(graphResult);

                    // Verify against expected results
                    if (graph.getId() == 1) {
                        verifyGraph1Results(primResult, kruskalResult);
                    } else if (graph.getId() == 2) {
                        verifyGraph2Results(primResult, kruskalResult);
                    }
                }

                // Create and write output data
                OutputData outputData = new OutputData(results);
                writeOutputFile(gson, outputData);

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
     * Write output data to JSON file
     */
    private static void writeOutputFile(Gson gson, OutputData outputData) throws IOException {
        String outputPath = Paths.get("ass_3_output.json").toAbsolutePath().toString();

        try (FileWriter writer = new FileWriter(outputPath)) {
            gson.toJson(outputData, writer);
            System.out.println("\n-- SUCCESS: Output file generated!");
            System.out.println("-- File: " + outputPath);
            System.out.println("-- Contains results for " + outputData.getResults().size() + " graphs");

            // Display summary of written data
            for (GraphResult result : outputData.getResults()) {
                System.out.println("\n--- Graph " + result.getGraph_id() + " Summary ---");
                System.out.println("Input: " + result.getInput_stats().getVertices() +
                        " vertices, " + result.getInput_stats().getEdges() + " edges");
                System.out.println("Prim: cost=" + result.getPrim().getTotal_cost() +
                        ", operations=" + result.getPrim().getOperations_count() +
                        ", time=" + String.format("%.2f", result.getPrim().getExecution_time_ms()) + "ms");
                System.out.println("Kruskal: cost=" + result.getKruskal().getTotal_cost() +
                        ", operations=" + result.getKruskal().getOperations_count() +
                        ", time=" + String.format("%.2f", result.getKruskal().getExecution_time_ms()) + "ms");
            }
        }
    }

    /**
     * Display results for a single algorithm
     */
    private static void displayAlgorithmResults(String algorithmName, AlgorithmResult result) {
        System.out.println("✅ " + algorithmName + " MST found!");
        System.out.println("-- Total cost: " + result.getTotal_cost());
        System.out.println("-- Operations count: " + result.getOperations_count());
        System.out.println("--  Execution time: " + String.format("%.2f", result.getExecution_time_ms()) + " ms");
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
        System.out.println("--  Prim time: " + String.format("%.2f", prim.getExecution_time_ms()) + " ms" +
                " vs Kruskal time: " + String.format("%.2f", kruskal.getExecution_time_ms()) + " ms");

        if (prim.getOperations_count() < kruskal.getOperations_count()) {
            System.out.println("-- Prim used fewer operations");
        } else if (prim.getOperations_count() > kruskal.getOperations_count()) {
            System.out.println("-- Kruskal used fewer operations");
        } else {
            System.out.println("-- Both algorithms used same number of operations");
        }

        if (prim.getExecution_time_ms() < kruskal.getExecution_time_ms()) {
            System.out.println("⚡ Prim was faster");
        } else if (prim.getExecution_time_ms() > kruskal.getExecution_time_ms()) {
            System.out.println("⚡ Kruskal was faster");
        } else {
            System.out.println("⚡ Both algorithms had similar execution time");
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