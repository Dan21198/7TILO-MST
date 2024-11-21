package osu;

import java.util.*;

public class PrimMST {
    public List<Edge> findMST(Graph graph) {
        List<Edge> mst = new ArrayList<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getLength));
        Set<Node> visited = new HashSet<>();

        Node startNode = graph.getNodes().iterator().next();
        visited.add(startNode);

        for (Edge edge : graph.getEdges()) {
            if (edge.getFrom().equals(startNode) || edge.getTo().equals(startNode)) {
                minHeap.add(edge);
            }
        }

        while (!minHeap.isEmpty() && mst.size() < graph.getNodes().size() - 1) {
            Edge edge = minHeap.poll();

            Node from = edge.getFrom();
            Node to = edge.getTo();

            if (!visited.contains(from) || !visited.contains(to)) {
                mst.add(edge);

                Node nextNode = visited.contains(from) ? to : from;
                visited.add(nextNode);

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
