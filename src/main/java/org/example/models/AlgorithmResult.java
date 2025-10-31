package org.example.models;

import java.util.List;

public class AlgorithmResult {
    private List<Edge> mst_edges;
    private int total_cost;
    private int operations_count;
    private double execution_time_ms;

    // Default constructor for Gson
    public AlgorithmResult() {}

    // Updated constructor with operations_count and execution_time_ms
    public AlgorithmResult(List<Edge> mst_edges, int total_cost, int operations_count, double execution_time_ms) {
        this.mst_edges = mst_edges;
        this.total_cost = total_cost;
        this.operations_count = operations_count;
        this.execution_time_ms = execution_time_ms;
    }

    // Getters and setters
    public List<Edge> getMst_edges() { return mst_edges; }
    public void setMst_edges(List<Edge> mst_edges) { this.mst_edges = mst_edges; }

    public int getTotal_cost() { return total_cost; }
    public void setTotal_cost(int total_cost) { this.total_cost = total_cost; }

    public int getOperations_count() { return operations_count; }
    public void setOperations_count(int operations_count) { this.operations_count = operations_count; }

    public double getExecution_time_ms() { return execution_time_ms; }
    public void setExecution_time_ms(double execution_time_ms) { this.execution_time_ms = execution_time_ms; }

    @Override
    public String toString() {
        return "AlgorithmResult{" +
                "mst_edges=" + mst_edges +
                ", total_cost=" + total_cost +
                ", operations_count=" + operations_count +
                ", execution_time_ms=" + execution_time_ms +
                '}';
    }
}