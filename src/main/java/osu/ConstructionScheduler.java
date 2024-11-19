package osu;

import java.util.*;

public class ConstructionScheduler {

    static class Node {
        String name;
        Node(String name) {
            this.name = name;
        }
        String getName() {
            return this.name;
        }
    }

    static class Edge {
        Node from;
        Node to;
        int length;

        Edge(Node from, Node to, int length) {
            this.from = from;
            this.to = to;
            this.length = length;
        }
    }

    static class Graph {
        private Map<String, Node> nodes = new HashMap<>();
        private List<Edge> edges = new ArrayList<>();

        public void addEdge(String from, String to, int length) {
            Node fromNode = nodes.computeIfAbsent(from, Node::new);
            Node toNode = nodes.computeIfAbsent(to, Node::new);
            edges.add(new Edge(fromNode, toNode, length));
        }

        public List<Node> getNodes() {
            return new ArrayList<>(nodes.values());
        }

        public List<Edge> getEdges() {
            return edges;
        }
    }

    static class DisjointSet {
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

    public static List<Edge> kruskalMST(Graph graph) {
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        edges.sort(Comparator.comparingInt(e -> e.length));

        List<Edge> mst = new ArrayList<>();
        DisjointSet disjointSet = new DisjointSet();

        for (Node node : graph.getNodes()) {
            disjointSet.makeSet(node);
        }

        for (Edge edge : edges) {
            Node root1 = disjointSet.find(edge.from);
            Node root2 = disjointSet.find(edge.to);
            if (root1 != root2) {
                mst.add(edge);
                disjointSet.union(edge.from, edge.to);
            }
        }

        return mst;
    }

    public static void scheduleWork(List<Edge> mst) {
        int totalKm = 0, totalDays = 0;
        List<String> workLog = new ArrayList<>();
        Set<String> visitedEdges = new HashSet<>();
        Set<Node> visitedNodes = new HashSet<>(); // Track nodes that have been visited

        for (Edge edge : mst) {
            // Skip edges where the destination node has been visited
            if (visitedNodes.contains(edge.to)) {
                continue;
            }

            String edgeKey = edge.from.getName() + "-" + edge.to.getName();
            String reverseEdgeKey = edge.to.getName() + "-" + edge.from.getName();

            // Ensure that an edge (in either direction) is processed only once
            if (visitedEdges.contains(edgeKey) || visitedEdges.contains(reverseEdgeKey)) {
                continue;
            }

            visitedEdges.add(edgeKey); // Mark the edge as visited

            // Mark the destination node as visited
            visitedNodes.add(edge.to);

            int remainingWork = edge.length;
            int dailyHours = 8;

            // Work on the edge in daily chunks
            while (remainingWork > 0) {
                int hoursWorked = Math.min(remainingWork, dailyHours);
                int kmLaid = hoursWorked;

                dailyHours -= hoursWorked;
                remainingWork -= hoursWorked;

                totalDays++;
                totalKm += kmLaid;

                String log = "[d_" + totalDays + "] " + edge.from.getName() + " -> " + edge.to.getName()
                        + ": " + hoursWorked + " hours, " + kmLaid + " km";
                workLog.add(log);

                if (remainingWork > 0) {
                    dailyHours = 8; // Reset hours for the next day
                }
            }

            // Only log travel if this is the first time encountering this pair of cities
            if (!visitedNodes.contains(edge.from)) {
                totalDays++;
                workLog.add("[d_" + totalDays + "] Travel: 1 hour (to next city)");
                visitedNodes.add(edge.from);
            }
        }

        // Print work logs
        workLog.forEach(System.out::println);

        // Summary
        System.out.println("-------------------------------------");
        System.out.println("Result: " + totalDays + " days, " + totalKm + " km");
    }



    public static void main(String[] args) {
        String[] data = {
                "(Lordaeron)-[2]-(Tristram)", "(Lordaeron)-[9]-(Arraken)", "(Tristram)-[2]-(Lordaeron)",
                "(Tristram)-[7]-(Arraken)", "(Tristram)-[12]-(Rivie)", "(Tristram)-[4]-(Minas Tirith)",
                "(Solitude)-[8]-(Rivie)", "(Arraken)-[16]-(Lannisport)", "(Arraken)-[9]-(Lordaeron)",
                "(Arraken)-[7]-(Tristram)", "(Arraken)-[24]-(Ankh Morpork)", "(Lannisport)-[16]-(Arraken)",
                "(Lannisport)-[10]-(Ankh Morpork)", "(Minas Tirith)-[4]-(Tristram)", "(Minas Tirith)-[3]-(Rivie)",
                "(Minas Tirith)-[8]-(Mordheim)", "(Minas Tirith)-[9]-(Gondolin)", "(Rivie)-[8]-(Solitude)",
                "(Rivie)-[12]-(Tristram)", "(Rivie)-[3]-(Minas Tirith)", "(Gondolin)-[9]-(Minas Tirith)",
                "(Gondolin)-[5]-(Mordheim)", "(Gondolin)-[15]-(Godrick's Hollow)", "(Gondolin)-[20]-(Mos Eisley)",
                "(Gondolin)-[5]-(Ankh Morpork)", "(Mordheim)-[8]-(Minas Tirith)", "(Mordheim)-[5]-(Gondolin)",
                "(Mordheim)-[1]-(Godrick's Hollow)", "(Ankh Morpork)-[10]-(Lannisport)",
                "(Ankh Morpork)-[24]-(Arraken)", "(Ankh Morpork)-[5]-(Gondolin)", "(Ankh Morpork)-[4]-(Mos Eisley)",
                "(Mos Eisley)-[4]-(Ankh Morpork)", "(Mos Eisley)-[20]-(Gondolin)", "(Mos Eisley)-[7]-(LV 426)",
                "(Godric's Hollow)-[1]-(Mordheim)", "(Godric's Hollow)-[15]-(Gondolin)",
                "(Godric's Hollow)-[3]-(LV 426)", "(LV 426)-[7]-(Mos Eisley)", "(LV 426)-[3]-(Godric's Hollow)"
        };

        Graph graph = new Graph();

        for (String edge : data) {
            String[] parts = edge.split("\\)-\\[|]-\\(");
            String from = parts[0].substring(1);
            int weight = Integer.parseInt(parts[1]);
            String to = parts[2].substring(0, parts[2].length() - 1);
            graph.addEdge(from, to, weight);
        }

        List<Edge> mst = kruskalMST(graph);

        scheduleWork(mst);
    }
}
