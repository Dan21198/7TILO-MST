package osu;

import java.util.*;

public class KruskalMST {
    public List<Edge> findMST(Graph graph) {
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        edges.sort(Comparator.comparingInt(Edge::getLength));

        List<Edge> mst = new ArrayList<>();
        DisjointSet disjointSet = new DisjointSet();

        for (Node node : graph.getNodes()) {
            disjointSet.makeSet(node);
        }

        for (Edge edge : edges) {
            Node root1 = disjointSet.find(edge.getFrom());
            Node root2 = disjointSet.find(edge.getTo());
            if (root1 != root2) {
                mst.add(edge);
                disjointSet.union(edge.getFrom(), edge.getTo());
            }
        }

        return mst;
    }
}

