package osu;

import java.util.*;

public class DisjointSet {
    private Map<Node, Node> parent = new HashMap<>();

    public void makeSet(Node node) {
        parent.put(node, node);
    }

    public Node find(Node node) {
        if (parent.get(node) != node) {
            parent.put(node, find(parent.get(node)));
        }
        return parent.get(node);
    }

    public void union(Node node1, Node node2) {
        Node root1 = find(node1);
        Node root2 = find(node2);
        if (root1 != root2) {
            parent.put(root1, root2);
        }
    }
}

