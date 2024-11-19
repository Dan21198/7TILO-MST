package osu;

import java.util.*;

public class Graph {
    private final Map<String, Node> nodes = new HashMap<>();
    private final Set<Edge> edges = new HashSet<>();  // Use a set to automatically avoid duplicate edges

    public void addEdge(String from, String to, int length) {
        Node fromNode = nodes.computeIfAbsent(from, Node::new);
        Node toNode = nodes.computeIfAbsent(to, Node::new);

        Edge edge = new Edge(fromNode, toNode, length);
        Edge reverseEdge = new Edge(toNode, fromNode, length);

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

