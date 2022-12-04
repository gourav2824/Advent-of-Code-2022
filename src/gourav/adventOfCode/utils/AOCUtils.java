package gourav.adventOfCode.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AOCUtils {
    public static List<String> getListOfStringsFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        List<String> list = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            list.add(input);
            input = sc.nextLine();
        }

        sc.close();
        return list;
    }
}
