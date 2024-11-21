package osu;

import java.util.*;

public class BoruvkaMST {
    public List<Edge> findMST(Graph graph) {
        List<Edge> mst = new ArrayList<>();
        DisjointSet disjointSet = new DisjointSet();

        for (Node node : graph.getNodes()) {
            disjointSet.makeSet(node);
        }

        int components = graph.getNodes().size();

        while (components > 1) {
            Map<Node, Edge> cheapestEdge = new HashMap<>();

            for (Edge edge : graph.getEdges()) {
                Node rootFrom = disjointSet.find(edge.getFrom());
                Node rootTo = disjointSet.find(edge.getTo());

                if (!rootFrom.equals(rootTo)) {
                    if (!cheapestEdge.containsKey(rootFrom) || edge.getLength() < cheapestEdge.get(rootFrom).getLength()) {
                        cheapestEdge.put(rootFrom, edge);
                    }
                    if (!cheapestEdge.containsKey(rootTo) || edge.getLength() < cheapestEdge.get(rootTo).getLength()) {
                        cheapestEdge.put(rootTo, edge);
                    }
                }
            }

            for (Edge edge : cheapestEdge.values()) {
                Node rootFrom = disjointSet.find(edge.getFrom());
                Node rootTo = disjointSet.find(edge.getTo());

                if (!rootFrom.equals(rootTo)) {
                    mst.add(edge);
                    disjointSet.union(edge.getFrom(), edge.getTo());
                    components--; // Two components merged.
                }
            }
        }

        return mst;
    }
}
