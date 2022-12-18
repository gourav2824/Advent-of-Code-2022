package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day18Puzzle1 {
    private static class Face {
        int x1, y1, z1;
        int x2, y2, z2;

        public Face(int x1, int y1, int z1, int x2, int y2, int z2) {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Face face = (Face) o;
            if (x1 == face.x1 && y1 == face.y1 && z1 == face.z1 && x2 == face.x2 && y2 == face.y2 && z2 == face.z2) {
                return true;
            } else {
                return x1 == face.x2 && y1 == face.y2 && z1 == face.z2 && x2 == face.x1 && y2 == face.y1 && z2 == face.z1;
            }
        }

        @Override
        public int hashCode() {
            return 0;           // returning same HashCode for all the faces
        }
    }

    public static void main(String[] args) {
        final List<int[]> listOfCubes = getListOfCubesFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(listOfCubes));
    }

    private static int puzzle1(List<int[]> listOfCubes) {
        final Set<Face> faces = new HashSet<>();
        final Set<Face> commonFaces = new HashSet<>();

        for (int[] cube : listOfCubes) {
            int x = cube[0];
            int y = cube[1];
            int z = cube[2];
            addFace(faces, commonFaces, new Face(x, y, z, x + 1, y + 1, z));
            addFace(faces, commonFaces, new Face(x, y, z, x, y + 1, z + 1));
            addFace(faces, commonFaces, new Face(x, y, z, x + 1, y, z + 1));

            x = cube[0] + 1;
            y = cube[1] + 1;
            z = cube[2] + 1;
            addFace(faces, commonFaces, new Face(x, y, z, x - 1, y - 1, z));
            addFace(faces, commonFaces, new Face(x, y, z, x, y - 1, z - 1));
            addFace(faces, commonFaces, new Face(x, y, z, x - 1, y, z - 1));
        }

        return faces.size() - commonFaces.size();
    }

    private static void addFace(Set<Face> faces, Set<Face> commonFaces, Face face) {
        if (faces.contains(face)) {
            commonFaces.add(face);
        } else {
            faces.add(face);
        }
    }

    public static List<int[]> getListOfCubesFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        final List<int[]> list = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            final String[] parts = input.split(",");
            list.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])});
            input = sc.nextLine();
        }

        sc.close();
        return list;
    }
}
