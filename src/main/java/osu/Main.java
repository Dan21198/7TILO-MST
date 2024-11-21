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
                "(Gondolin)-[5]-(Mordheim)", "(Gondolin)-[15]-(Godric's Hollow)", "(Gondolin)-[20]-(Mos Eisley)",
                "(Gondolin)-[5]-(Ankh Morpork)", "(Mordheim)-[8]-(Minas Tirith)", "(Mordheim)-[5]-(Gondolin)",
                "(Mordheim)-[1]-(Godric's Hollow)", "(Ankh Morpork)-[10]-(Lannisport)",
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
        List<Edge> mstKruskal = kruskal.findMST(graph);

        PrimMST prim = new PrimMST();
        List<Edge> mstPrim = prim.findMST(graph);

        BoruvkaMST boruvka = new BoruvkaMST();
        List<Edge> mstBoruvka = boruvka.findMST(graph);

        WorkScheduler scheduler = new WorkScheduler();

        System.out.println("Kruskal:");
        scheduler.scheduleAndPrintWork(mstKruskal);
        int daysKruskal = scheduler.getTotalDays();
        int kmKruskal = scheduler.getTotalKm();
        scheduler.reset();
        System.out.println();

        System.out.println("Prim:");
        scheduler.scheduleAndPrintWork(mstPrim);
        int daysPrim = scheduler.getTotalDays();
        int kmPrim = scheduler.getTotalKm();
        scheduler.reset();
        System.out.println();

        System.out.println("Borůvka:");
        scheduler.scheduleAndPrintWork(mstBoruvka);
        int daysBoruvka = scheduler.getTotalDays();
        int kmBoruvka = scheduler.getTotalKm();
        scheduler.reset();
        System.out.println();

        printComparison(daysKruskal, kmKruskal, daysPrim, kmPrim, daysBoruvka, kmBoruvka);
    }

    private static void printComparison(int daysKruskal, int kmKruskal, int daysPrim, int kmPrim,
                                        int daysBoruvka, int kmBoruvka) {
        System.out.println("========================================");
        System.out.println("Srovnání algoritmů pro hledání MST:");
        System.out.println("----------------------------------------");
        System.out.printf("Algoritmus   | Počet dnů | Celková vzdálenost%n");
        System.out.println("----------------------------------------");
        System.out.printf("Kruskal      | %-10d | %-16d%n", daysKruskal, kmKruskal);
        System.out.printf("Prim         | %-10d | %-16d%n", daysPrim, kmPrim);
        System.out.printf("Borůvka      | %-10d | %-16d%n", daysBoruvka, kmBoruvka);
        System.out.println("========================================");
    }
}