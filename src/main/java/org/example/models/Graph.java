package org.example.models;

import java.util.List;

public class Graph {
    private int id;
    private List<String> nodes;
    private List<Edge> edges;

    // Default constructor for Gson
    public Graph() {}

    public Graph(int id, List<String> nodes, List<Edge> edges) {
        this.id = id;
        this.nodes = nodes;
        this.edges = edges;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<String> getNodes() { return nodes; }
    public void setNodes(List<String> nodes) { this.nodes = nodes; }

    public List<Edge> getEdges() { return edges; }
    public void setEdges(List<Edge> edges) { this.edges = edges; }

    @Override
    public String toString() {
        return "Graph{id=" + id + ", nodes=" + nodes + ", edges=" + edges + '}';
    }
}