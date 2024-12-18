package osu;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkScheduler {
    private int dayCounter = 1;
    private int totalDays = 0;
    private int totalKm = 0;
    private final Set<String> visitedNodes = new HashSet<>();

    public void reset() {
        dayCounter = 1;
        totalDays = 0;
        totalKm = 0;
        visitedNodes.clear();
    }

    public void scheduleAndPrintWork(List<Edge> mst) {
        System.out.println("Minimum Spanning Tree (MST):");

        for (Edge edge : mst) {
            scheduleWorkForEdge(edge);
        }

        System.out.println("-------------------------------------");
        System.out.println("Result: " + totalDays + " days, " + totalKm + " km");
    }

    private void scheduleWorkForEdge(Edge edge) {
        int dailyHours = 8;
        int travelTime = 0;

        if (!visitedNodes.contains(edge.getFrom().getName()) && !visitedNodes.contains(edge.getTo().getName())) {
            travelTime = 1;
        }

        int remainingWork = edge.getLength();
        int totalWorkTime = remainingWork + travelTime;

        while (totalWorkTime > 0) {
            int hoursWorked = Math.min(totalWorkTime, dailyHours);

            totalWorkTime -= hoursWorked;

            int kmLaid = Math.min(remainingWork, hoursWorked);

            if (travelTime > 0) {
                travelTime = 0;
                kmLaid -= (remainingWork > 8) ? 1 : 0;
            }

            totalKm += kmLaid;
            remainingWork -= kmLaid;

            System.out.println("[d_" + dayCounter + "] " + edge.getFrom().getName() + " -> "
                    + edge.getTo().getName() + ": "
                    + hoursWorked + " hours, "
                    + kmLaid + " km");

            dayCounter++;
            totalDays++;
        }
        visitedNodes.add(edge.getFrom().getName());
        visitedNodes.add(edge.getTo().getName());
    }

    public int getTotalDays() {
        return totalDays;
    }

    public int getTotalKm() {
        return totalKm;
    }
}
