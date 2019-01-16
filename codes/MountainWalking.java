import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MountainWalking {
    public static void main(String[] args) throws IOException {
        //input.txt file should be given, checks it.
        if (args.length != 1) {
            throw new Error("input should be [input.txt]");
        }
        //matrix arraylist for constructing mountain    .
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
        Scanner input = new Scanner(new File(args[0]));
        Scanner line = new Scanner(input.nextLine());
        //start point
        Point start = new Point(line.nextInt(), line.nextInt());
        line = new Scanner(input.nextLine());
        //goal point
        Point goal = new Point(line.nextInt(), (line.nextInt()));
        //reads the 2D mountain with heights.
        while (input.hasNextLine()) {
            Scanner colReader = new Scanner(input.nextLine());
            ArrayList row = new ArrayList();
            while (colReader.hasNextDouble()) {
                row.add(colReader.nextDouble());
            }
            matrix.add(row);
        }
        //matrix form of matrix arraylist.
        double[][] matrix1 = new double[matrix.size()][matrix.get(0).size()];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                matrix1[i][j] = matrix.get(i).get(j);
            }
        }
        //output file name format
        String output = args[0].substring(0, args[0].indexOf(".txt"));

        //writes output for DFS.
        BufferedWriter writer = new BufferedWriter(new FileWriter(output + "_dfs_out.txt"));
        DFSmoun dfSmoun = new DFSmoun(matrix1, start, goal);
        writer.write(dfSmoun.run());
        writer.close();
        //writes output for BFS
        writer = new BufferedWriter(new FileWriter(output+"_bfs_out.txt"));
        BFSmoun bfsmoun = new BFSmoun(matrix1, start, goal);
        writer.write(bfsmoun.run());
        writer.close();
        //writes output for A*.
        writer = new BufferedWriter(new FileWriter(output+"_a_star_out.txt"));
        AStarMoun aStarMoun = new AStarMoun(matrix1, start, goal);
        writer.write(aStarMoun.run());
        writer.close();

    }
}