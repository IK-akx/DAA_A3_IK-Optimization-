package org.example.models;

import java.util.List;

public class InputData {
    private List<Graph> graphs;

    // Default constructor for Gson
    public InputData() {}

    public InputData(List<Graph> graphs) {
        this.graphs = graphs;
    }

    // Getters and setters
    public List<Graph> getGraphs() { return graphs; }
    public void setGraphs(List<Graph> graphs) { this.graphs = graphs; }

    @Override
    public String toString() {
        return "InputData{graphs=" + graphs + '}';
    }
}
