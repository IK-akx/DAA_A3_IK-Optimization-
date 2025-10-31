package org.example.models;

import java.util.List;

public class OutputData {
    private List<GraphResult> results;

    // Default constructor for Gson
    public OutputData() {}

    public OutputData(List<GraphResult> results) {
        this.results = results;
    }

    // Getters and setters
    public List<GraphResult> getResults() { return results; }
    public void setResults(List<GraphResult> results) { this.results = results; }

    @Override
    public String toString() {
        return "OutputData{results=" + results + '}';
    }
}
