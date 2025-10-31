package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.models.InputData;
import org.example.algorithms.PrimsAlgorithm;
import org.example.models.AlgorithmResult;

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

                // Test Prim's algorithm on each graph
                for (int i = 0; i < inputData.getGraphs().size(); i++) {
                    var graph = inputData.getGraphs().get(i);
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("--- Testing Graph " + graph.getId() + " ---");
                    System.out.println("Nodes: " + graph.getNodes().size() + " - " + graph.getNodes());
                    System.out.println("Edges: " + graph.getEdges().size());

                    // Run Prim's algorithm
                    System.out.println("\n Running Prim's Algorithm...");
                    var primResult = PrimsAlgorithm.findMST(graph);

                    // Display Prim's results
                    System.out.println("✅ Prim's MST found!");
                    System.out.println("Total cost: " + primResult.getTotal_cost());
                    System.out.println("Operations count: " + primResult.getOperations_count());
                    System.out.println("⏱Execution time: " + primResult.getExecution_time_ms() + " ms");
                    System.out.println("MST edges:");

                    for (var edge : primResult.getMst_edges()) {
                        System.out.println("   " + edge.getFrom() + " --(" + edge.getWeight() + ")--> " + edge.getTo());
                    }

                    // Verify against expected results for graph 1
                    if (graph.getId() == 1) {
                        verifyGraph1Results(primResult);
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
     * Verify Prim's results for Graph 1 against expected values
     */
    private static void verifyGraph1Results(AlgorithmResult primResult) {
        System.out.println("\n ---- Verifying Graph 1 results against expected values... ---");

        int expectedCost = 16;
        int actualCost = primResult.getTotal_cost();

        System.out.println("Expected total cost: " + expectedCost);
        System.out.println("Actual total cost: " + actualCost);

        if (actualCost == expectedCost) {
            System.out.println("✅ Total cost verification: PASSED");
        } else {
            System.out.println("❌ Total cost verification: FAILED");
        }

        // Check if we have the expected number of edges (V-1 = 4 edges for 5 vertices)
        int expectedEdges = 4;
        int actualEdges = primResult.getMst_edges().size();
        System.out.println("Expected edges in MST: " + expectedEdges);
        System.out.println("Actual edges in MST: " + actualEdges);

        if (actualEdges == expectedEdges) {
            System.out.println("✅ MST edges count verification: PASSED");
        } else {
            System.out.println("❌ MST edges count verification: FAILED");
        }
    }
}