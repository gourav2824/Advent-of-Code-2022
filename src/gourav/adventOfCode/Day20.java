package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day20 {
    private static class Num {
        int val;
        int pos;

        public Num(int val, int pos) {
            this.val = val;
            this.pos = pos;
        }
    }

    public static void main(String[] args) {
        final List<Num> listOfNumbers = getListOfNumbersFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(listOfNumbers));
    }

    private static int puzzle1(List<Num> nums) {
        final int n = nums.size();
        mixTheNumbers(nums);

        int index = -1;
        for (int i = 0; i < n; i++) {
            if (nums.get(i).val == 0) {
                index = i;
                break;
            }
        }

        int sum = 0;

        for (int i = 1; i <= 3000; i++) {
            index = (index + 1) % n;
            if (i == 1000 || i == 2000 || i == 3000) {
                sum += nums.get(index).val;
            }
        }

        return sum;
    }

    private static void mixTheNumbers(List<Num> nums) {
        final int n = nums.size();
        for (int pos = 0; pos < n; pos++) {
            int index = getNumIndex(nums, pos);
            int val = nums.get(index).val;

            boolean forward = val >= 0;
            val = Math.abs(val);

            while (val-- > 0) {
                if (forward) {
                    if (index == n - 1) {
                        final Num num = nums.remove(n - 1);
                        nums.add(0, num);
                        index = 0;
                    }
                    swap(nums, index, index + 1);
                    index++;
                }
                else {
                    if (index == 0) {
                        final Num num = nums.remove(0);
                        nums.add(num);
                        index = n - 1;
                    }
                    swap(nums, index, index - 1);
                    index--;
                }
            }

            if (forward) {
                if (index == n - 1) {
                    final Num num = nums.remove(n - 1);
                    nums.add(0, num);
                }
            } else {
                if (index == 0) {
                    final Num num = nums.remove(0);
                    nums.add(num);
                }
            }
        }
    }

    private static int getNumIndex(List<Num> nums, int pos) {
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i).pos == pos) {
                return i;
            }
        }
        return -1;
    }

    private static void swap(List<Num> nums, int i, int j) {
        final Num temp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, temp);
    }

    private static List<Num> getListOfNumbersFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        int position = 0;
        List<Num> nums = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            nums.add(new Num(Integer.parseInt(input), position++));
            input = sc.nextLine();
        }

        sc.close();
        return nums;
    }
}
