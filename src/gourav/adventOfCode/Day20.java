package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day20 {
    private static class Num {
        long val;
        final int pos;

        public Num(int val, int pos) {
            this.val = val;
            this.pos = pos;
        }
    }

    public static void main(String[] args) {
        final List<Num> nums = getListOfNumsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(new ArrayList<>(nums)));
        System.out.println("Puzzle 2 Answer = " + puzzle2(new ArrayList<>(nums)));
    }

    private static long puzzle1(List<Num> nums) {
        mixTheNumbers(nums);
        return getSum(nums, getIndexOfZero(nums));
    }

    private static long puzzle2(List<Num> nums) {
        final long decryptionKey = 811589153;
        for (Num num : nums) {
            num.val *= decryptionKey;
        }
        for (int i = 0; i < 10; i++) {
            mixTheNumbers(nums);
        }
        return getSum(nums, getIndexOfZero(nums));
    }

    private static void mixTheNumbers(List<Num> nums) {
        final int n = nums.size();
        for (int pos = 0; pos < n; pos++) {
            int index = getNumIndex(nums, pos);
            long val = nums.get(index).val;

            boolean forward = val >= 0;
            val = getValue(Math.abs(val), n);

            while (val-- > 0) {
                index = rotateTheNumbers(nums, forward, index);
                if (forward) {
                    swap(nums, index, index + 1);
                    index++;
                } else {
                    swap(nums, index, index - 1);
                    index--;
                }
            }

            rotateTheNumbers(nums, forward, index);
        }
    }

    private static int rotateTheNumbers(List<Num> nums, boolean forward, int index) {
        final int n = nums.size();
        if (forward) {
            if (index == n - 1) {
                final Num num = nums.remove(n - 1);
                nums.add(0, num);
                index = 0;
            }
        } else {
            if (index == 0) {
                final Num num = nums.remove(0);
                nums.add(num);
                index = n - 1;
            }
        }
        return index;
    }

    private static long getSum(List<Num> nums, int index) {
        long sum = 0;
        for (int i = 1; i <= 3000; i++) {
            index = (index + 1) % nums.size();
            if (i == 1000 || i == 2000 || i == 3000) {
                sum += nums.get(index).val;
            }
        }
        return sum;
    }

    private static long getValue(long val, int n) {
        if (val / n > n) {
            return (val % n) + getValue(val / n, n);
        }
        return (val % n) + (val / n);
    }

    private static int getNumIndex(List<Num> nums, int pos) {
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i).pos == pos) {
                return i;
            }
        }
        return -1;
    }

    private static int getIndexOfZero(List<Num> nums) {
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i).val == 0) {
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

    private static List<Num> getListOfNumsFromInput() {
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
