package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Day16 {
    private static class Valve {
        String label;
        int flowRate;
        List<Valve> neighbors;

        public Valve(String label) {
            this.label = label;
            this.flowRate = 0;
            this.neighbors = new ArrayList<>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || this.getClass() != o.getClass()) return false;
            final Valve valve = (Valve) o;
            return Objects.equals(this.label, valve.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }
    }

    public static void main(String[] args) {
        final Map<String, Valve> valveMap = getValveMapFromInput();
        System.out.println("Calculating...");
        System.out.println("Puzzle 1 Answer = " + puzzle1(valveMap));
    }

    private static int puzzle1(Map<String, Valve> valveMap) {
        final Valve src = valveMap.get("AA");
        return getMaxPressure(src, new HashSet<>(), 0, 30);
    }

    private static int getMaxPressure(Valve valve, Set<Valve> openValves, int pressure, int minutes) {
        if (minutes == 0) {
            System.out.println(pressure);
            return pressure;
        }

        for (Valve openValve : openValves) {
            pressure += openValve.flowRate;
        }

        int max = 0;

        for (Valve neighbor : valve.neighbors) {
            max = Math.max(max, getMaxPressure(neighbor, openValves, pressure, minutes - 1));
        }

        if (!openValves.contains(valve)) {
            openValves.add(valve);
            for (Valve neighbor : valve.neighbors) {
                max = Math.max(max, getMaxPressure(neighbor, openValves, pressure, minutes - 1));
            }
            openValves.remove(valve);
        }

        return max;
    }

    private static Map<String, Valve> getValveMapFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        final Map<String, Valve> valveMap = new HashMap<>();
        StringBuilder input = new StringBuilder(sc.nextLine());

        while (input.length() > 0) {
            input.append(",");
            final String[] parts = input.toString().split(" ");
            final Valve valve = addValve(valveMap, parts[1]);
            valve.flowRate = Integer.parseInt(parts[4].substring(parts[4].indexOf('=') + 1, parts[4].indexOf(';')));

            for (int i = 9; i < parts.length; i++) {
                final Valve neighbor = addValve(valveMap, parts[i].substring(0, parts[i].indexOf(",")));
                valve.neighbors.add(neighbor);
            }

            input = new StringBuilder(sc.nextLine());
        }

        sc.close();
        return valveMap;
    }

    private static Valve addValve(Map<String, Valve> valveMap, String valveLabel) {
        if (!valveMap.containsKey(valveLabel)) {
            valveMap.put(valveLabel, new Valve(valveLabel));
        }
        return valveMap.get(valveLabel);
    }
}
