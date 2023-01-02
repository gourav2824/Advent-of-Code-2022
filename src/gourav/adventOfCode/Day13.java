package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day13 {
    public static void main(String[] args) {
        final List<List<String>> pairs = getPairsOfPacketsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(pairs));
    }

    private static int puzzle1(List<List<String>> pairs) {
        int sum = 0;
        for (int i = 0; i < pairs.size(); i++) {
            System.out.println("Pair " + (i + 1) + ":");
            System.out.println("Packet 1 = " + pairs.get(i).get(0));
            System.out.println("Packet 2 = " + pairs.get(i).get(1));

            if (compare(pairs.get(i).get(0), pairs.get(i).get(1))) {
                System.out.println("Pair " + (i + 1) + " -> in the right order");
                sum += (i + 1);
            } else {
                System.out.println("Pair " + (i + 1) + " -> not in the right order");
            }
        }
        return sum;
    }

    private static boolean compare(String left, String right) {
        int n1 = left.length(), n2 = right.length(), i = 0, j = 0;

        if (n1 == 0 && n2 == 0) {
            return true;
        } else if (n1 == 0) {
            return true;
        } else if (n2 == 0) {
            System.out.println("Right has size 0 : " + left + " & " + right);
            return false;
        }

        if (left.charAt(i) == '[' && right.charAt(j) == '[') {
            while (i < n1 - 1 && j < n2 - 1) {
                i++;
                j++;

                final String s1 = getNextElement(left, i);
                final String s2 = getNextElement(right, j);

                System.out.println("s1 = " + s1);
                System.out.println("s2 = " + s2);

                if (!compare(s1, s2)) {
                    System.out.println("Not equal : " + s1 + " & " + s2);
                    return false;
                }

                i += s1.length();
                j += s2.length();
            }

            if (i == n1 - 1 && j == n2 - 1) {
                return true;
            } else if (i == n1 - 1) {
                return true;
            } else {
                System.out.println("Right runs out of items : " + left + " (" + i + ")" + " & " + right + " (" + j + ")");
                return false;
            }
        }

        else if (left.charAt(i) == '[' || right.charAt(j) == '[') {
            if (left.charAt(i) == '[') {
                i++;
            } else {
                j++;
            }

            final String s1 = getNextElement(left, i);
            final String s2 = getNextElement(right, j);

            System.out.println("s1 = " + s1);
            System.out.println("s2 = " + s2);

            return compare(s1, s2);
        }

        else {
            int val1 = Integer.parseInt(left);
            int val2 = Integer.parseInt(right);
            return val1 <= val2;
        }
    }

    private static String getNextElement(String packet, int index) {
        String str;
        if (packet.charAt(index) == ']') {
            return "";
        } else if (packet.charAt(index) == '[') {
            int count = 1, end = index + 1;
            while (count > 0) {
                if (packet.charAt(end) == '[') {
                    count++;
                } else if (packet.charAt(end) == ']') {
                    count--;
                }
                end++;
            }
            str = packet.substring(index, end);
        } else {
            if (packet.startsWith("10", index)) {
                str = "10";
            } else {
                str = String.valueOf(packet.charAt(index));
            }
        }
        return str;
    }

    private static List<List<String>> getPairsOfPacketsFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        List<List<String>> pairs = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            final List<String> pair = new ArrayList<>();
            pair.add(input);
            input = sc.nextLine();
            pair.add(input);
            pairs.add(pair);

            sc.nextLine();
            input = sc.nextLine();
        }

        sc.close();
        return pairs;
    }
}
