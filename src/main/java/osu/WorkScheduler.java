package osu;

import java.util.List;

public class WorkScheduler {
    private int dayCounter = 1; // To track the day for the output
    private int totalDays = 0;  // To track total days
    private int totalKm = 0;    // To track total kilometers laid

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
        int travelTime = 1; // 1 hour for travel on the first day
        int remainingWork = edge.getLength();
        int totalWorkTime = remainingWork + travelTime;

        while (totalWorkTime > 0) {
            int hoursWorked = Math.min(totalWorkTime, dailyHours);

            // Include travel time only on the first day
            if (travelTime > 0) {
                hoursWorked++; // Account for travel time
                travelTime = 0;
            }

            totalWorkTime -= hoursWorked;

            // Calculate kilometers laid
            int kmLaid = Math.min(hoursWorked, remainingWork);
            totalKm += kmLaid;
            remainingWork -= kmLaid;

            // Log work for the day
            System.out.println("[d_" + dayCounter + "] " + edge.getFrom().getName() + " -> "
                    + edge.getTo().getName() + ": "
                    + hoursWorked + " hours, "
                    + kmLaid + " km");

            dayCounter++;
            totalDays++;
        }
    }
}
