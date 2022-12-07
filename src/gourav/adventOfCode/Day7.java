package gourav.adventOfCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day7 {
    public static void main(String[] args) {
        final List<String> input = getListOfStringsFromInput();
        System.out.println("Total Space = " + puzzle1(input));
        System.out.println("Puzzle 1 Answer = " + puzzle1Answer);
        System.out.println("Puzzle 2 Answer = " + puzzle2(root));
    }

    private static long puzzle1Answer = 0;
    private static final Dir root = new Dir();
    private static final TreeSet<Long> sizes = new TreeSet<>();

    public static class Dir {
        Map<String, Dir> childDirs;
        Map<String, Long> fileSizes;

        public Dir() {
            childDirs = new HashMap<>();
            fileSizes = new HashMap<>();
        }
    }

    private static long puzzle1(List<String> input) {
        final int n = input.size();
        final Stack<Dir> st = new Stack<>();
        st.push(root);

        for (int i = 0; i < n; i++) {
            final String command = input.get(i);
            if (command.split(" ")[1].equals("cd")) {
                final String dirName = command.split(" ")[2];
                if (dirName.equals("..")) {
                    st.pop();
                }
                else {
                    if (!st.peek().childDirs.containsKey(dirName)) {
                        st.peek().childDirs.put(dirName, new Dir());
                    }
                    st.push(st.peek().childDirs.get(dirName));
                }
            }
            else {
                i++;
                while (i < n && input.get(i).charAt(0) != '$') {
                    final String content = input.get(i);
                    if (content.split(" ")[0].equals("dir")) {
                        final String dirName = content.split(" ")[1];
                        if (!st.peek().childDirs.containsKey(dirName)) {
                            st.peek().childDirs.put(dirName, new Dir());
                        }
                    }
                    else {
                        final String fileName = content.split(" ")[1];
                        long fileSize = Long.parseLong(content.split(" ")[0]);
                        st.peek().fileSizes.put(fileName, fileSize);
                    }
                    i++;
                }
                i--;
            }
        }

        return getDirSize(root);
    }

    private static long puzzle2(Dir root) {
        final long usedSpace = getDirSize(root);
        final long requiredSpace = usedSpace - 40000000;
        return sizes.ceiling(requiredSpace);
    }

    private static long getDirSize(Dir dir) {
        long size = 0;
        for (long val : dir.fileSizes.values()) {
            size += val;
        }

        for (Dir childDir : dir.childDirs.values()) {
            size += getDirSize(childDir);
        }

        if (size <= 100000) {
            puzzle1Answer += size;
        }

        sizes.add(size);
        return size;
    }
}
