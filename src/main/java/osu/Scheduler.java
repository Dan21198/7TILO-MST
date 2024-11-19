package osu;

import java.util.*;

public class Scheduler {
    private int totalKm = 0;
    private int totalDays = 0;
    private final List<String> workLog = new ArrayList<>();
    private final Set<String> visitedEdges = new HashSet<>();
    private final Set<Node> visitedNodes = new HashSet<>();

    public void scheduleWork(List<Edge> mst) {
        for (Edge edge : mst) {
            // Skip if the destination node has already been visited
            if (visitedNodes.contains(edge.getTo())) {
                continue;
            }

            String edgeKey = edge.getFrom().getName() + "-" + edge.getTo().getName();
            String reverseEdgeKey = edge.getTo().getName() + "-" + edge.getFrom().getName();

            // Skip if the edge or reverse edge has already been processed
            if (visitedEdges.contains(edgeKey) || visitedEdges.contains(reverseEdgeKey)) {
                continue;
            }

            visitedEdges.add(edgeKey); // Mark this edge as visited

            // Mark the destination node as visited
            visitedNodes.add(edge.getTo());

            int remainingWork = edge.getLength();
            int dailyHours = 8;

            // Add 1 hour of travel time for moving to the new city
            int travelTime = 1;
            int totalWorkTime = remainingWork + travelTime;  // Total time including travel

            // Work on the edge in daily chunks, including the travel time
            while (totalWorkTime > 0) {
                int hoursWorked = Math.min(totalWorkTime, dailyHours);
                int kmLaid = hoursWorked;

                // Add 1 hour of travel time the first time we visit this node
                if (!visitedNodes.contains(edge.getFrom())) {
                    hoursWorked++; // Add 1 hour for travel
                    travelTime = 0; // Only add travel time once
                }

                dailyHours -= hoursWorked;
                totalWorkTime -= hoursWorked;

                totalDays++;
                totalKm += kmLaid;

                // Log the work and travel combined
                String log = "[d_" + totalDays + "] " + edge.getFrom().getName() + " -> " + edge.getTo().getName()
                        + ": " + hoursWorked + " hours, " + kmLaid + " km";

                workLog.add(log);

                if (totalWorkTime > 0) {
                    dailyHours = 8; // Reset hours for the next day
                }
            }

            visitedNodes.add(edge.getFrom());
        }

        printWorkLogs();
    }




    private void printWorkLogs() {
        workLog.forEach(System.out::println);
        System.out.println("-------------------------------------");
        System.out.println("Result: " + totalDays + " days, " + totalKm + " km");
    }
}
