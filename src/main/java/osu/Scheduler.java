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

            int remainingWork = edge.getLength();
            int dailyHours = 8;
            int totalWorkTime = remainingWork;

            // Initialize travel time; will only be added the first time a node is visited
            boolean isFirstVisit = !visitedNodes.contains(edge.getFrom());

            int travelTime = 0;

            // Add travel time only the first time we visit the source node
            if (isFirstVisit) {
                travelTime = 1;  // 1 hour for travel
                totalWorkTime += travelTime; // Add travel time to total work time
            }

            // Work on the edge in daily chunks, excluding the travel time in the daily work hours
            while (totalWorkTime > 0) {
                int hoursWorked = Math.min(totalWorkTime, dailyHours);
                int kmLaid = hoursWorked;

                if (travelTime > 0) {
                    hoursWorked++;
                    travelTime = 0;
                }

                totalWorkTime -= hoursWorked;

                totalDays++;
                totalKm += kmLaid;

                // Log the work (travel time not included in hours worked)
                String log = "[d_" + totalDays + "] " + edge.getFrom().getName() + " -> " + edge.getTo().getName()
                        + ": " + hoursWorked + " hours, " + kmLaid + " km";
                workLog.add(log);
                System.out.println(log);
            }

            visitedNodes.add(edge.getFrom());
            visitedNodes.add(edge.getTo());
            System.out.println(Arrays.toString(visitedNodes.stream().map(Node::getName).toArray(String[]::new)));
        }

        printWorkLogs();
    }

    private void printWorkLogs() {
        workLog.forEach(System.out::println);
        System.out.println("-------------------------------------");
        System.out.println("Result: " + totalDays + " days, " + totalKm + " km");
    }
}
