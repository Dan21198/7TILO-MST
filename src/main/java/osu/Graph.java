package osu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Graph {
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public Node addNode(String name) {
        Node node = new Node(name);
        nodes.add(node);
        return node;
    }

    public void addEdge(String from, String to, int length) {
        Node fromNode = nodes.stream().filter(n -> n.getName().equals(from)).findFirst().orElseGet(() -> addNode(from));
        Node toNode = nodes.stream().filter(n -> n.getName().equals(to)).findFirst().orElseGet(() -> addNode(to));
        edges.add(new Edge(fromNode, toNode, length));
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
