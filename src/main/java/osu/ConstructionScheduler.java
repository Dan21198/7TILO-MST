package osu;

import java.util.*;

public class ConstructionScheduler {
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

        DisjointSet disjointSet = new DisjointSet();
        for (Edge edge : edges) {
            disjointSet.makeSet(edge.from);
            disjointSet.makeSet(edge.to);
        }

        List<Edge> mst = new ArrayList<>();
        for (Edge edge : edges) {
            if (disjointSet.find(edge.from) != disjointSet.find(edge.to)) {
                mst.add(edge);
                disjointSet.union(edge.from, edge.to);
            }
        }
        return mst;
    }

    public static void scheduleWork(List<Edge> mst) {
        int totalKm = 0, totalDays = 0;
        List<String> workLog = new ArrayList<>();

        for (Edge edge : mst) {
            int remainingWork = edge.length;
            int dailyHours = 8;

            while (remainingWork > 0) {
                int hoursWorked = Math.min(remainingWork, dailyHours);
                int kmLaid = hoursWorked;

                dailyHours -= hoursWorked;
                remainingWork -= hoursWorked;

                totalDays++;
                totalKm += kmLaid;

                // Format output to match the desired format
                String log = "[d_" + totalDays + "] " + edge.from.getName() + " -> " + edge.to.getName()
                        + ": " + hoursWorked + " hours, " + kmLaid + " km";
                workLog.add(log);

                if (remainingWork > 0) {
                    dailyHours = 8; // Reset hours for the next day
                }
            }

            // Add 1 day for travel to the next city if needed
            totalDays++;
            workLog.add("[d_" + totalDays + "] Travel: 1 hour (to next city)");
        }
        workLog.forEach(System.out::println);

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