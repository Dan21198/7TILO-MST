package osu;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();

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

        for (String edge : data) {
            String[] parts = edge.split("\\)-\\[|]-\\(");
            String from = parts[0].substring(1);
            int weight = Integer.parseInt(parts[1]);
            String to = parts[2].substring(0, parts[2].length() - 1);
            graph.addEdge(from, to, weight);
        }

        KruskalMST kruskal = new KruskalMST();
        List<Edge> mst = kruskal.findMST(graph);

        Scheduler scheduler = new Scheduler();
        scheduler.scheduleWork(mst);
    }
}