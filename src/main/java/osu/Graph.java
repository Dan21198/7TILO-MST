package osu;

import java.util.*;

public class Graph {
    private Map<String, Node> nodes = new HashMap<>();
    private Set<Edge> edges = new HashSet<>();  // Use a set to automatically avoid duplicate edges

    public void addEdge(String from, String to, int length) {
        Node fromNode = nodes.computeIfAbsent(from, Node::new);
        Node toNode = nodes.computeIfAbsent(to, Node::new);

        // Create a sorted edge so that we consider only one direction for each pair
        Edge edge = new Edge(fromNode, toNode, length);
        Edge reverseEdge = new Edge(toNode, fromNode, length);

        // Add only the edge with a consistent order (from -> to)
        if (fromNode.getName().compareTo(toNode.getName()) < 0) {
            edges.add(edge);
        } else {
            edges.add(reverseEdge);
        }
    }

    public List<Node> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }
}

