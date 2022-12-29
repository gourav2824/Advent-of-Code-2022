package gourav.adventOfCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day25 {
    public static void main(String[] args) {
        final List<String> snafuNumbers = getListOfStringsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(snafuNumbers));
    }

    private static String puzzle1(List<String> snafuNumbers) {
        long sum = 0;
        for (String snafuNumber : snafuNumbers) {
            final long decimal = convertSnafuNumberToDecimal(snafuNumber);
            sum += decimal;
        }
        return convertDecimalToSnafuNumber(sum);
    }

    private static long convertSnafuNumberToDecimal(String snafuNumber) {
        final Map<Character, Long> snafuToDecimalMap = getSnafuToDecimalMap();
        long decimal = 0, multiplier = (long) Math.pow(5, snafuNumber.length() - 1);

        for (char ch : snafuNumber.toCharArray()) {
            decimal += snafuToDecimalMap.get(ch) * multiplier;
            multiplier /= 5;
        }

        return decimal;
    }

    private static String convertDecimalToSnafuNumber(long decimal) {
        final Map<Long, Character> decimalToSnafuMap = getDecimalToSnafuMap();
        StringBuilder snafuNumber = new StringBuilder();

        while (decimal > 0) {
            final long rem = decimal % 5;
            decimal = decimal / 5;
            snafuNumber.insert(0, decimalToSnafuMap.get(rem));

            if (rem > 2) {
                decimal += 1;
            }
        }

        return snafuNumber.toString();
    }

    private static Map<Character, Long> getSnafuToDecimalMap() {
        final Map<Character, Long> snafuToDecimalMap = new HashMap<>();
        snafuToDecimalMap.put('=', -2L);
        snafuToDecimalMap.put('-', -1L);
        snafuToDecimalMap.put('0', 0L);
        snafuToDecimalMap.put('1', 1L);
        snafuToDecimalMap.put('2', 2L);
        return snafuToDecimalMap;
    }

    private static Map<Long, Character> getDecimalToSnafuMap() {
        final Map<Long, Character> snafuToDecimalMap = new HashMap<>();
        snafuToDecimalMap.put(0L, '0');
        snafuToDecimalMap.put(1L, '1');
        snafuToDecimalMap.put(2L, '2');
        snafuToDecimalMap.put(3L, '=');
        snafuToDecimalMap.put(4L, '-');
        return snafuToDecimalMap;
    }
}
