package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.models.InputData;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== MST Transportation Network Optimization ===");

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Read and parse input JSON file
            String inputPath = Paths.get("C:\\Users\\kusta\\OneDrive\\Рабочий стол\\Java.OOP\\DAA_A6_IK\\src\\test\\resources\\ass_3_input.json").toAbsolutePath().toString();
            System.out.println("Reading input file: " + inputPath);

            InputData inputData = gson.fromJson(new FileReader(inputPath), InputData.class);

            // Display basic information about parsed data
            if (inputData != null && inputData.getGraphs() != null) {
                System.out.println("✅ Successfully parsed input data!");
                System.out.println("Number of graphs: " + inputData.getGraphs().size());

                // Display details for each graph
                for (int i = 0; i < inputData.getGraphs().size(); i++) {
                    var graph = inputData.getGraphs().get(i);
                    System.out.println("\n--- Graph " + (i + 1) + " ---");
                    System.out.println("ID: " + graph.getId());
                    System.out.println("Nodes: " + graph.getNodes().size() + " - " + graph.getNodes());
                    System.out.println("Edges: " + graph.getEdges().size());

                    // Display all edges
                    for (var edge : graph.getEdges()) {
                        System.out.println("  " + edge.getFrom() + " --(" + edge.getWeight() + ")--> " + edge.getTo());
                    }
                }
            } else {
                System.out.println("❌ No graphs found in input file!");
            }

        } catch (IOException e) {
            System.err.println("❌ Error reading input file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}