package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
    }

    private static final Map<String, Valve> valveMap = getValveMapFromInput();
    private static final Map<String, Integer> valveIndexesMap = getValveIndexesMap();

    public static void main(String[] args) {
        System.out.println("Calculating...");
        System.out.println("Puzzle 1 Answer = " + puzzle1());
        System.out.println("Puzzle 2 Answer = " + puzzle2());
    }

    private static int puzzle1() {
        final Valve source = valveMap.get("AA");
        final Map<Integer, Map<Long, Map<Integer, Integer>>> dp = new HashMap<>();
        return getMaxPressureByMe(source, 0, 30, dp);
    }

    private static int puzzle2() {
        final Valve source = valveMap.get("AA");
        final Map<Boolean, Map<Integer, Map<Long, Map<Integer, Integer>>>> dp = new HashMap<>();
        dp.put(true, new HashMap<>());
        dp.put(false, new HashMap<>());
        return getMaxPressureByMeAndElephant(source, true, 0, 26, dp);
    }

    private static int getMaxPressureByMe(Valve valve, long openValves, int minutes,
                                          Map<Integer, Map<Long, Map<Integer, Integer>>> dp) {
        if (minutes == 0) {
            return 0;
        }

        final int index = valveIndexesMap.get(valve.label);
        final Integer value = getValue(dp, index, openValves, minutes);
        if (value != null) return value;

        int max = 0;
        final long mask = 1L << index;

        if (valve.flowRate > 0 && (openValves & mask) == 0) {
            final long newOpenValves = openValves | mask;
            final int pressure = valve.flowRate * (minutes - 1);
            max = Math.max(max, pressure + getMaxPressureByMe(valve, newOpenValves, minutes - 1, dp));
        }

        for (Valve neighbor : valve.neighbors) {
            max = Math.max(max, getMaxPressureByMe(neighbor, openValves, minutes - 1, dp));
        }

        addValue(dp, index, openValves, minutes, max);
        return max;
    }

    private static int getMaxPressureByMeAndElephant(Valve valve, boolean isMe, long openValves, int minutes,
                                                     Map<Boolean, Map<Integer, Map<Long, Map<Integer, Integer>>>> dp) {
        if (minutes == 0) {
            return isMe ? getMaxPressureByMeAndElephant(valveMap.get("AA"), false, openValves, 26, dp) : 0;
        }

        final int index = valveIndexesMap.get(valve.label);
        final Integer value = getValue(dp.get(isMe), index, openValves, minutes);
        if (value != null) return value;

        int max = 0;
        final long mask = 1L << index;

        if (valve.flowRate > 0 && (openValves & mask) == 0) {
            final long newOpenValves = openValves | mask;
            final int pressure = valve.flowRate * (minutes - 1);
            max = Math.max(max, pressure + getMaxPressureByMeAndElephant(valve, isMe, newOpenValves, minutes - 1, dp));
        }

        for (Valve neighbor : valve.neighbors) {
            max = Math.max(max, getMaxPressureByMeAndElephant(neighbor, isMe, openValves, minutes - 1, dp));
        }

        addValue(dp.get(isMe), index, openValves, minutes, max);
        return max;
    }

    private static Integer getValue(Map<Integer, Map<Long, Map<Integer, Integer>>> dp, int index, long openValves, int minutes) {
        if (dp.containsKey(index)) {
            if (dp.get(index).containsKey(openValves)) {
                if (dp.get(index).get(openValves).containsKey(minutes)) {
                    return dp.get(index).get(openValves).get(minutes);
                }
            }
        }
        return null;
    }

    private static void addValue(Map<Integer, Map<Long, Map<Integer, Integer>>> dp, int index, long openValves, int minutes, int max) {
        if (!dp.containsKey(index)) {
            dp.put(index, new HashMap<>());
        }
        if (!dp.get(index).containsKey(openValves)) {
            dp.get(index).put(openValves, new HashMap<>());
        }
        dp.get(index).get(openValves).put(minutes, max);
    }

    private static Map<String, Integer> getValveIndexesMap() {
        final Map<String, Integer> valveIndexesMap = new HashMap<>();
        int index = 0;
        for (String valve : valveMap.keySet()) {
            valveIndexesMap.put(valve, index++);
        }
        return valveIndexesMap;
    }

    private static Map<String, Valve> getValveMapFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        final Map<String, Valve> valveMap = new HashMap<>();
        StringBuilder input = new StringBuilder(sc.nextLine());

        while (input.length() > 0) {
            input.append(",");
            final String[] parts = input.toString().split(" ");
            final Valve valve = addValveInValveMap(valveMap, parts[1]);
            valve.flowRate = Integer.parseInt(parts[4].substring(parts[4].indexOf('=') + 1, parts[4].indexOf(';')));

            for (int i = 9; i < parts.length; i++) {
                final Valve neighbor = addValveInValveMap(valveMap, parts[i].substring(0, parts[i].indexOf(",")));
                valve.neighbors.add(neighbor);
            }

            input = new StringBuilder(sc.nextLine());
        }

        sc.close();
        return valveMap;
    }

    private static Valve addValveInValveMap(Map<String, Valve> valveMap, String valveLabel) {
        if (!valveMap.containsKey(valveLabel)) {
            valveMap.put(valveLabel, new Valve(valveLabel));
        }
        return valveMap.get(valveLabel);
    }
}
