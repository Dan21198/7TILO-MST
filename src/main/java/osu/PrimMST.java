package osu;

import java.util.*;

public class PrimMST {
    public List<Edge> findMST(Graph graph) {
        List<Edge> mst = new ArrayList<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getLength));
        Set<Node> visited = new HashSet<>();

        // Start with an arbitrary node.
        Node startNode = graph.getNodes().iterator().next();
        visited.add(startNode);

        // Add edges from the starting node to the minHeap.
        for (Edge edge : graph.getEdges()) {
            if (edge.getFrom().equals(startNode) || edge.getTo().equals(startNode)) {
                minHeap.add(edge);
            }
        }

        // While there are edges to process:
        while (!minHeap.isEmpty() && mst.size() < graph.getNodes().size() - 1) {
            // Get the smallest edge.
            Edge edge = minHeap.poll();

            // Determine the next node to visit.
            Node from = edge.getFrom();
            Node to = edge.getTo();

            // Only add the edge if it connects to an unvisited node.
            if (!visited.contains(from) || !visited.contains(to)) {
                mst.add(edge);

                // Mark the newly visited node.
                Node nextNode = visited.contains(from) ? to : from;
                visited.add(nextNode);

                // Add all edges connected to the new node to the minHeap, avoiding revisiting visited nodes.
                for (Edge adjacentEdge : graph.getEdges()) {
                    if ((adjacentEdge.getFrom().equals(nextNode) && !visited.contains(adjacentEdge.getTo())) ||
                            (adjacentEdge.getTo().equals(nextNode) && !visited.contains(adjacentEdge.getFrom()))) {
                        minHeap.add(adjacentEdge);
                    }
                }
            }
        }

        return mst;
    }
}
