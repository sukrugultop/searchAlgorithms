import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Lab {
    public static void main(String[] args) throws IOException {
        //input.txt file should be given, checks it.
        if (args.length != 1) {
            throw new Error("input should be [input.txt]");
        }
        //matrix arraylist for constructing labyrinth.
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        Scanner input = new Scanner(new File(args[0]));
        Scanner line = new Scanner(input.nextLine());
        //start point
        Point start = new Point(line.nextInt(),line.nextInt());
        line = new Scanner(input.nextLine());
        //goal point
        Point goal = new Point(line.nextInt(),(line.nextInt()));
        //reads the 2D labyrinth.
        while(input.hasNextLine())
        {
            Scanner colReader = new Scanner(input.nextLine());
            ArrayList row = new ArrayList();
            while(colReader.hasNextInt()) {
                row.add(colReader.nextInt());
            }
            matrix.add(row);
        }
        //matrix form of matrix arraylist.
        int[][] matrix1 = new int[matrix.size()][matrix.get(0).size()];
        for (int i=0; i<matrix1.length; i++){
            for (int j=0; j<matrix1[0].length; j++){
                matrix1[i][j] = matrix.get(i).get(j);
            }
        }
        //output file name format
        String output = args[0].substring(0,args[0].indexOf(".txt"));

        //writes output for DFS.
        BufferedWriter writer = new BufferedWriter(new FileWriter(output+"_dfs_out.txt"));
        DFSLab dfslab = new DFSLab(matrix1, start, goal);
        writer.write(dfslab.run());
        writer.close();
        //writes output for BFS
        writer = new BufferedWriter(new FileWriter(output+"_bfs_out.txt"));
        BFSLab bfslab = new BFSLab(matrix1, start, goal);
        writer.write(bfslab.run());
        writer.close();
        //writes output for A*.
        writer = new BufferedWriter(new FileWriter(output+"_a_star_out.txt"));
        AStarLab aStarLab = new AStarLab(matrix1, start,goal);
        writer.write(aStarLab.run());
        writer.close();
    }
}
