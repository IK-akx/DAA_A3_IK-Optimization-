package org.example.models;

public class GraphResult {
    private int graph_id;
    private InputStats input_stats;
    private AlgorithmResult prim;
    private AlgorithmResult kruskal;

    // Default constructor for Gson
    public GraphResult() {}

    public GraphResult(int graph_id, InputStats input_stats, AlgorithmResult prim, AlgorithmResult kruskal) {
        this.graph_id = graph_id;
        this.input_stats = input_stats;
        this.prim = prim;
        this.kruskal = kruskal;
    }

    // Getters and setters
    public int getGraph_id() { return graph_id; }
    public void setGraph_id(int graph_id) { this.graph_id = graph_id; }

    public InputStats getInput_stats() { return input_stats; }
    public void setInput_stats(InputStats input_stats) { this.input_stats = input_stats; }

    public AlgorithmResult getPrim() { return prim; }
    public void setPrim(AlgorithmResult prim) { this.prim = prim; }

    public AlgorithmResult getKruskal() { return kruskal; }
    public void setKruskal(AlgorithmResult kruskal) { this.kruskal = kruskal; }

    @Override
    public String toString() {
        return "GraphResult{" +
                "graph_id=" + graph_id +
                ", input_stats=" + input_stats +
                ", prim=" + prim +
                ", kruskal=" + kruskal +
                '}';
    }
}