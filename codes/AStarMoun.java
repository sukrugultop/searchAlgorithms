import java.awt.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarMoun {
    //main input of problem(should contains heights of that coordinate)
    double[][] matrix;
    //hold the information about whether corresponding cell is visited or not.
    int[][] visited;
    //for the final path each cell holds the information where it is came from.
    Point[][] cameFrom;
    // start and goal points of the mountain.
    Point start, goal;
    // G values of the Points, the path length of the current node from start node.
    double[][] distances;
    /**
     * Initializes the A* for mountain.
     * @param matrix 2D mountain representation with heights.
     * @param start start point of labyrinth.
     * @param goal goal point of labyrinth.
     */
    AStarMoun(double[][] matrix, Point start, Point goal) {
        this.matrix = matrix;
        this.start = start;
        this.goal = goal;
        this.visited = new int[matrix.length][matrix[0].length];
        this.distances = new double[matrix.length][matrix[0].length];
        this.cameFrom = new Point[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                //default values for visited is 0.
                visited[i][j] = 0;
                //default Point for came from is P(-1,-1).
                cameFrom[i][j] = new Point(-1, -1);
                //default G is infinity(in java max value.)
                distances[i][j] = Integer.MAX_VALUE;
            }
        }

    }


    String run() {
        //openset for the a*, priority queue with comparator which is minumum distance+euclidean distance from goal. If they are equal biggest index first.
        PriorityQueue<Point> openSet = new PriorityQueue<>(5, new Comparator<Point>() {
            @Override
            public int compare(Point point, Point t1) {
                if (euchDist(point, goal) + distances[point.x][point.y] > euchDist(t1, goal) + distances[t1.x][t1.y]) {
                    return 1;
                } else if (euchDist(point, goal) + distances[point.x][point.y] < euchDist(t1, goal) + distances[t1.x][t1.y]) {
                    return -1;
                } else {
                    if (point.x * matrix.length + point.y > t1.x * matrix.length + t1.y)
                        return -1;
                    else {
                        return 1;
                    }
                }
            }
        });
        //start node's distance is 0.
        distances[start.x][start.y] = 0;
        //adds start node to openlist.
        openSet.add(start);
        Point lastVisited = start;
        //main loop of the a*,
        while (!openSet.isEmpty()) {
            //current node.
            Point curr = openSet.remove();
            //update lastvisited node.
            lastVisited = curr;
            //checks whether current node is goal or not.
            if (curr.x == goal.x && curr.y == goal.y) {
                visited[curr.x][curr.y] = 1;
                break;
            }
            //updates current visited status.
            visited[curr.x][curr.y] = 1;
            //adds neighbours to openset.
            addNeighbours(curr.x, curr.y, openSet);
        }

        ////////output part//////
        String s="";
        for (int i=0 ; i<visited.length; i++){
            for (int j=0; j<visited[0].length; j++){
                s+=visited[i][j] + " ";
            }
            s+="\n";
        }

        Point p = cameFrom[lastVisited.x][lastVisited.y];
        String s1 = lastVisited.x+" "+lastVisited.y;
        double d = euchDist(lastVisited, p);
        int n=2;
        while (!(p.x==start.x && p.y==start.y)){
            n++;
            s1 = p.x + " " + p.y + "\n"+ s1;
            d+=euchDist(cameFrom[p.x][p.y], p);
            p = cameFrom[p.x][p.y];
        }
        s1= n+ "\n"+start.x +" "+ start.y +"\n" + s1 +"\n"+ String.format("%.2f", d);
        return s+s1;
    }

    /**
     * calculates Euclidean distance between two points.
     * @param p1 first point
     * @param p2 second point
     * @return manhattan distance
     */
    double euchDist(Point p1, Point p2){
        return Math.sqrt(Math.pow((p1.x-p2.x),2)+Math.pow((p1.y-p2.y),2)+Math.pow((matrix[p1.x][p1.y]-matrix[p2.x][p2.y]),2));
    }
    /**
     * adds nieghbours of current node(i,j) to openset and updates came from information.
     *
     * checks all four neighbours of the current node. If it satisfies following constraints it is added to openlist:
     * 1) the Euclidean distance between current node and neighbour should be smaller than 1.
     * 2) the calculated distance should be smaller than former distance(better distance(g) value)
     * 3) node should be unvisited.
     *
     * @param i x coordinate of current node
     * @param j y coordinate of current node
     * @param openset openset of a*
     */
    public void addNeighbours(int i, int j, PriorityQueue<Point> openset) {
        if (i > 0 && visited[i - 1][j] == 0 && Math.abs(matrix[i-1][j]-matrix[i][j])<=1) {
            double tmp = distances[i][j] + euchDist(new Point(i,j),new Point(i-1,j));
            if (tmp < distances[i - 1][j]) {
                distances[i - 1][j] = tmp;
                openset.add(new Point(i - 1, j));
                cameFrom[i - 1][j] = new Point(i, j);
            }

        }
        if (j > 0 && visited[i][j - 1] == 0 && Math.abs(matrix[i][j-1]-matrix[i][j])<=1) {
            double tmp = distances[i][j] + euchDist(new Point(i,j),new Point(i,j-1));
            if (tmp < distances[i][j - 1]) {
                distances[i][j - 1] = tmp;
                openset.add(new Point(i, j - 1));
                cameFrom[i][j - 1] = new Point(i, j);
            }

        }
        if (i < matrix.length - 1 && visited[i + 1][j] == 0 && Math.abs(matrix[i+1][j]-matrix[i][j])<=1) {
            double tmp = distances[i][j] + euchDist(new Point(i,j),new Point(i+1,j));
            if (tmp < distances[i + 1][j]) {
                distances[i + 1][j] = tmp;
                openset.add(new Point(i + 1, j));
                cameFrom[i + 1][j] = new Point(i, j);
            }
        }
        if (j < matrix[0].length - 1 && visited[i][j + 1] == 0 && Math.abs(matrix[i][j+1]-matrix[i][j])<=1) {
            double tmp = distances[i][j] + euchDist(new Point(i,j),new Point(i,j+1));
            if (tmp < distances[i][j + 1]) {
                distances[i][j + 1] = tmp;
                openset.add(new Point(i, j + 1));
                cameFrom[i][j + 1] = new Point(i, j);
            }
        }
    }
}
