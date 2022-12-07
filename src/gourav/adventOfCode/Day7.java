package gourav.adventOfCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day7 {
    private static int puzzle1Answer = 0;
    private static final TreeSet<Integer> dirSizes = new TreeSet<>();

    private static class Dir {
        Map<String, Dir> childDirs;
        Map<String, Integer> fileSizes;

        private Dir() {
            childDirs = new HashMap<>();
            fileSizes = new HashMap<>();
        }
    }

    public static void main(String[] args) {
        final Dir root = createTreeFromInput(getListOfStringsFromInput());
        final int totalUsedSpace = calculateDirSize(root);
        System.out.println("Puzzle 1 Answer = " + puzzle1Answer);
        System.out.println("Puzzle 2 Answer = " + puzzle2(totalUsedSpace));
    }

    private static int puzzle2(int totalUsedSpace) {
        final int requiredSpace = totalUsedSpace - 40000000;
        final Integer answer = dirSizes.ceiling(requiredSpace);
        return answer == null ? -1 : answer;
    }

    private static int calculateDirSize(Dir dir) {
        int size = 0;
        for (int val : dir.fileSizes.values()) {
            size += val;
        }

        for (Dir childDir : dir.childDirs.values()) {
            size += calculateDirSize(childDir);
        }

        if (size <= 100000) {
            puzzle1Answer += size;
        }

        dirSizes.add(size);
        return size;
    }

    private static Dir createTreeFromInput(List<String> input) {
        final Stack<Dir> st = new Stack<>();
        final Dir root = new Dir();
        int i = 0, n = input.size();
        st.push(root);

        while (i < n) {
            final String command = input.get(i++);
            if (command.split(" ")[1].equals("cd")) {
                final String dirName = command.split(" ")[2];
                if (dirName.equals("..")) {
                    st.pop();
                } else {
                    if (!st.peek().childDirs.containsKey(dirName)) {
                        st.peek().childDirs.put(dirName, new Dir());
                    }
                    st.push(st.peek().childDirs.get(dirName));
                }
            } else {
                while (i < n && input.get(i).charAt(0) != '$') {
                    final String content = input.get(i);
                    if (content.split(" ")[0].equals("dir")) {
                        final String dirName = content.split(" ")[1];
                        if (!st.peek().childDirs.containsKey(dirName)) {
                            st.peek().childDirs.put(dirName, new Dir());
                        }
                    } else {
                        final String fileName = content.split(" ")[1];
                        int fileSize = Integer.parseInt(content.split(" ")[0]);
                        st.peek().fileSizes.put(fileName, fileSize);
                    }
                    i++;
                }
            }
        }

        return root;
    }
}
