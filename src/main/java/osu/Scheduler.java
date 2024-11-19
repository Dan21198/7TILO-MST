package osu;

import java.util.*;

public class Scheduler {
    private int totalKm = 0;
    private int totalDays = 0;
    private List<String> workLog = new ArrayList<>();
    private Set<String> visitedEdges = new HashSet<>();
    private Set<Node> visitedNodes = new HashSet<>();

    public void scheduleWork(List<Edge> mst) {
        for (Edge edge : mst) {
            if (visitedNodes.contains(edge.getTo())) {
                continue;
            }

            String edgeKey = edge.getFrom().getName() + "-" + edge.getTo().getName();
            String reverseEdgeKey = edge.getTo().getName() + "-" + edge.getFrom().getName();

            if (visitedEdges.contains(edgeKey) || visitedEdges.contains(reverseEdgeKey)) {
                continue;
            }

            visitedEdges.add(edgeKey);
            visitedNodes.add(edge.getTo());

            int remainingWork = edge.getLength();
            int dailyHours = 8;

            while (remainingWork > 0) {
                int hoursWorked = Math.min(remainingWork, dailyHours);
                int kmLaid = hoursWorked;

                dailyHours -= hoursWorked;
                remainingWork -= hoursWorked;

                totalDays++;
                totalKm += kmLaid;

                String log = "[d_" + totalDays + "] " + edge.getFrom().getName() + " -> " + edge.getTo().getName()
                        + ": " + hoursWorked + " hours, " + kmLaid + " km";
                workLog.add(log);

                if (remainingWork > 0) {
                    dailyHours = 8;
                }
            }

            if (!visitedNodes.contains(edge.getFrom())) {
                totalDays++;
                workLog.add("[d_" + totalDays + "] Travel: 1 hour (to next city)");
                visitedNodes.add(edge.getFrom());
            }
        }

        printWorkLogs();
    }

    private void printWorkLogs() {
        workLog.forEach(System.out::println);
        System.out.println("-------------------------------------");
        System.out.println("Result: " + totalDays + " days, " + totalKm + " km");
    }
}
