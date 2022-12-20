package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Day19 {
    private static class Resources {
        int ore;
        int clay;
        int obsidian;
        int geode;

        public Resources(int ore, int clay, int obsidian, int geode) {
            this.ore = ore;
            this.clay = clay;
            this.obsidian = obsidian;
            this.geode = geode;
        }
    }

    private static class Blueprint {
        final Resources oreRobotCost;
        final Resources clayRobotCost;
        final Resources obsidianRobotCost;
        final Resources geodeRobotCost;

        public Blueprint(int oreRobotCost, int clayRobotCost, int[] obsidianRobotCost, int[] geodeRobotCost) {
            this.oreRobotCost = new Resources(oreRobotCost, 0, 0, 0);
            this.clayRobotCost = new Resources(clayRobotCost, 0, 0, 0);
            this.obsidianRobotCost = new Resources(obsidianRobotCost[0], obsidianRobotCost[1], 0, 0);
            this.geodeRobotCost = new Resources(geodeRobotCost[0], 0, geodeRobotCost[1], 0);
        }
    }

    private static class State {
        int ore;
        int clay;
        int obsidian;
        int geode;
        int oreRobots;
        int clayRobots;
        int obsidianRobots;
        int geodeRobots;
        int minutes;

        public State(int ore, int clay, int obsidian, int geode, int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots, int minutes) {
            this.ore = ore;
            this.clay = clay;
            this.obsidian = obsidian;
            this.geode = geode;
            this.oreRobots = oreRobots;
            this.clayRobots = clayRobots;
            this.obsidianRobots = obsidianRobots;
            this.geodeRobots = geodeRobots;
            this.minutes = minutes;
        }
    }

    public static void main(String[] args) {
        final List<Blueprint> listOfBlueprints = getListOfBlueprintsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(listOfBlueprints));
        System.out.println("Puzzle 2 Answer = " + puzzle2(listOfBlueprints));
    }

    private static int puzzle1(List<Blueprint> listOfBlueprints) {
        int total = 0;
        for (int i = 0; i < listOfBlueprints.size(); i++) {
            final Resources resources = new Resources(0, 0, 0, 0);
            final Resources robots = new Resources(1, 0, 0, 0);
            final int geodes = bfs(listOfBlueprints.get(i),
                    resources.ore, resources.clay, resources.obsidian, resources.geode,
                    robots.ore, robots.clay, robots.obsidian, robots.geode, 24);
            System.out.println(geodes);
            total += geodes * (i + 1);
        }
        return total;
    }

    private static int puzzle2(List<Blueprint> listOfBlueprints) {
        int total = 1;
        for (int i = 0; i < 3; i++) {
            final Resources resources = new Resources(0, 0, 0, 0);
            final Resources robots = new Resources(1, 0, 0, 0);
            final int geodes = bfs(listOfBlueprints.get(i),
                    resources.ore, resources.clay, resources.obsidian, resources.geode,
                    robots.ore, robots.clay, robots.obsidian, robots.geode, 32);
            System.out.println(geodes);
            total *= geodes;
        }
        return total;
    }

    private static int bfs(Blueprint blueprint, int ore, int clay, int obsidian, int geode,
                           int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots,
                           int minutes) {
        int max = 0;
        Queue<State> queue = new LinkedList<>();
        queue.add(new State(ore, clay, obsidian, geode, oreRobots, clayRobots, obsidianRobots, geodeRobots, minutes));

        Set<List<Integer>> visited = new HashSet<>();

        while (queue.size() > 0) {
            final State rem = queue.remove();
            max = Math.max(max, rem.geode);

            if (rem.minutes == 0) {
                continue;
            }

            System.out.println(rem);

            ore = rem.ore;
            clay = rem.clay;
            obsidian = rem.obsidian;
            geode = rem.geode;
            oreRobots = rem.oreRobots;
            clayRobots = rem.clayRobots;
            obsidianRobots = rem.obsidianRobots;
            geodeRobots = rem.geodeRobots;
            minutes = rem.minutes;

            final int maxOre = Math.max(blueprint.oreRobotCost.ore, Math.max(blueprint.clayRobotCost.ore, Math.max(blueprint.obsidianRobotCost.ore, blueprint.geodeRobotCost.ore)));

            if (oreRobots >= maxOre) {
                oreRobots = maxOre;
            }

            if (clayRobots >= blueprint.obsidianRobotCost.clay) {
                clayRobots = blueprint.obsidianRobotCost.clay;
            }

            if (obsidianRobots >= blueprint.geodeRobotCost.obsidian) {
                obsidianRobots = blueprint.geodeRobotCost.obsidian;
            }

            if (ore >= minutes * maxOre - oreRobots * (minutes - 1)) {
                ore = minutes * maxOre - oreRobots * (minutes - 1);
            }

            if (clay >= minutes * blueprint.obsidianRobotCost.clay - clayRobots * (minutes - 1)) {
                clay = minutes * blueprint.obsidianRobotCost.clay - clayRobots * (minutes - 1);
            }

            if (obsidian >= minutes * blueprint.geodeRobotCost.obsidian - obsidianRobots * (minutes - 1)) {
                obsidian = minutes * blueprint.geodeRobotCost.obsidian - obsidianRobots * (minutes - 1);
            }

            final List<Integer> state = Arrays.asList(ore, clay, obsidian, geode, oreRobots, clayRobots, obsidianRobots, geodeRobots, minutes);
            if (visited.contains(state)) {
                continue;
            }
            visited.add(state);

            queue.add(new State(ore + oreRobots, clay + clayRobots, obsidian + obsidianRobots, geode + geodeRobots,
                    oreRobots, clayRobots, obsidianRobots, geodeRobots, minutes - 1));

            if (isPossibleToBuildRobot(blueprint.oreRobotCost, ore, clay, obsidian)) {
                queue.add(new State(ore - blueprint.oreRobotCost.ore + oreRobots, clay + clayRobots, obsidian + obsidianRobots, geode + geodeRobots,
                        oreRobots + 1, clayRobots, obsidianRobots, geodeRobots, minutes - 1));
            }

            if (isPossibleToBuildRobot(blueprint.clayRobotCost, ore, clay, obsidian)) {
                queue.add(new State(ore - blueprint.clayRobotCost.ore + oreRobots, clay + clayRobots, obsidian + obsidianRobots, geode + geodeRobots,
                        oreRobots, clayRobots + 1, obsidianRobots, geodeRobots, minutes - 1));
            }

            if (isPossibleToBuildRobot(blueprint.obsidianRobotCost, ore, clay, obsidian)) {
                queue.add(new State(ore - blueprint.obsidianRobotCost.ore + oreRobots, clay - blueprint.obsidianRobotCost.clay + clayRobots, obsidian + obsidianRobots, geode + geodeRobots,
                        oreRobots, clayRobots, obsidianRobots + 1, geodeRobots, minutes - 1));
            }

            if (isPossibleToBuildRobot(blueprint.geodeRobotCost, ore, clay, obsidian)) {
                queue.add(new State(ore - blueprint.geodeRobotCost.ore + oreRobots, clay + clayRobots, obsidian - blueprint.geodeRobotCost.obsidian + obsidianRobots, geode + geodeRobots,
                        oreRobots, clayRobots, obsidianRobots, geodeRobots + 1, minutes - 1));
            }
        }

        return max;
    }

    private static boolean isPossibleToBuildRobot(Resources robotCost, int ore, int clay, int obsidian) {
        return ore >= robotCost.ore && clay >= robotCost.clay && obsidian >= robotCost.obsidian;
    }

    public static List<Blueprint> getListOfBlueprintsFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        final List<Blueprint> blueprints = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            final String[] parts = input.split(" ");
            int oreRobotCost = Integer.parseInt(parts[0]);
            int clayRobotCost = Integer.parseInt(parts[1]);
            int[] obsidianRobotCost = new int[]{Integer.parseInt(parts[2]), Integer.parseInt(parts[3])};
            int[] geodeRobotCost = new int[]{Integer.parseInt(parts[4]), Integer.parseInt(parts[5])};

            blueprints.add(new Blueprint(oreRobotCost, clayRobotCost, obsidianRobotCost, geodeRobotCost));
            input = sc.nextLine();
        }

        sc.close();
        return blueprints;
    }
}
