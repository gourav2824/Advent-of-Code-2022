package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static gourav.adventOfCode.utils.AOCUtils.getStringFromInput;

public class Day17 {
    private static class Rock {
        int shape;
        List<long[]> points;

        public Rock(int shape, long bottom) {
            this.shape = shape;
            this.points = new ArrayList<>();
            createRock(bottom);
        }

        private void createRock(long bottom) {
            if (shape == 0) {
                points.addAll(Arrays.asList(new long[]{2, bottom}, new long[]{3, bottom}, new long[]{4, bottom}, new long[]{5, bottom}));
            } else if (shape == 1) {
                points.addAll(Arrays.asList(new long[]{3, bottom}, new long[]{2, bottom + 1}, new long[]{3, bottom + 1}, new long[]{4, bottom + 1}, new long[]{3, bottom + 2}));
            } else if (shape == 2) {
                points.addAll(Arrays.asList(new long[]{2, bottom}, new long[]{3, bottom}, new long[]{4, bottom}, new long[]{4, bottom + 1}, new long[]{4, bottom + 2}));
            } else if (shape == 3) {
                points.addAll(Arrays.asList(new long[]{2, bottom}, new long[]{2, bottom + 1}, new long[]{2, bottom + 2}, new long[]{2, bottom + 3}));
            } else {
                points.addAll(Arrays.asList(new long[]{2, bottom}, new long[]{3, bottom}, new long[]{2, bottom + 1}, new long[]{3, bottom + 1}));
            }
        }
    }

    public static void main(String[] args) {
        final String jetPattern = getStringFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(jetPattern));
    }

    private static long puzzle1(String jetPattern) {
        final int n = jetPattern.length(), shapes = 5;
        long rocks = 2022, top = 0;
        int shape = 0, jet = 0;

        final Set<List<Long>> chamber = new HashSet<>();

        while (rocks-- > 0) {
            final Rock rock = new Rock(shape++ % shapes, top + 4);

            do {
                final char force = jetPattern.charAt(jet++ % n);
                if (force == '<') {
                    moveLeft(chamber, rock);
                } else {
                    moveRight(chamber, rock);
                }
            } while (moveDown(chamber, rock));

            for (long[] point : rock.points) {
                chamber.add(Arrays.asList(point[0], point[1]));
                top = Math.max(top, point[1]);
            }
        }

        return top;
    }

    private static void moveLeft(Set<List<Long>> chamber, Rock rock) {
        final int leftBoundary = -1;
        final List<long[]> newPoints = new ArrayList<>();
        for (long[] point : rock.points) {
            if (point[0] - 1 == leftBoundary || chamber.contains(Arrays.asList(point[0] - 1, point[1]))) {
                return;
            }
            newPoints.add(new long[]{point[0] - 1, point[1]});
        }
        rock.points = newPoints;
    }

    private static void moveRight(Set<List<Long>> chamber, Rock rock) {
        final int rightBoundary = 7;
        final List<long[]> newPoints = new ArrayList<>();
        for (long[] point : rock.points) {
            if (point[0] + 1 == rightBoundary || chamber.contains(Arrays.asList(point[0] + 1, point[1]))) {
                return;
            }
            newPoints.add(new long[]{point[0] + 1, point[1]});
        }
        rock.points = newPoints;
    }

    private static boolean moveDown(Set<List<Long>> chamber, Rock rock) {
        final List<long[]> newPoints = new ArrayList<>();
        for (long[] point : rock.points) {
            if (point[1] - 1 == 0 || chamber.contains(Arrays.asList(point[0], point[1] - 1))) {
                return false;
            }
            newPoints.add(new long[]{point[0], point[1] - 1});
        }
        rock.points = newPoints;
        return true;
    }
}
