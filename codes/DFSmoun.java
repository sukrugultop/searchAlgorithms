import java.awt.*;
import java.util.PriorityQueue;
import java.util.Stack;

public class DFSmoun {
    //main input of problem(should contains heights of that coordinate)
    double[][] matrix;
    //hold the information about whether corresponding cell is visited or not.
    int[][] visited;
    //for the final path each cell holds the information where it is came from.
    Point[][] cameFrom;
    // start and goal points of the mountain.
    Point start, goal;

    /**
     * Initializes the DFS for labyrinth.
     * @param matrix 2D mountain representation with heights.
     * @param start start point of DFS.
     * @param goal goal point of DFS.
     */
    DFSmoun(double[][] matrix, Point start, Point goal){
        this.matrix = matrix;
        this.start = start;
        this.goal = goal;
        this.visited = new int[matrix.length][matrix[0].length];
        this.cameFrom = new Point[matrix.length][matrix[0].length];
        for (int i=0; i<matrix.length; i++){
            for (int j=0; j<matrix[0].length; j++){
                //default values for visited is 0.
                visited[i][j] = 0;
                //default Point for came from is P(-1,-1).
                cameFrom[i][j] = new Point(-1,-1);
            }
        }
    }
    /**
     * Returns the possible Points where it can go from coordinate (i,j)
     *
     * If neighbour node(which can be 4 positions at max of current node which are up,left,right,down) is not 1 and not visited it adds to pq.
     * @param i x coordinate of current point.
     * @param j y corrdinate of current point.
     * @return priority queue according to indexes of neighbours node.
     */
    public PriorityQueue<Point> getNeighbours(int i, int j){
        // initialize with size 5 and comparator for index.
        PriorityQueue<Point> pq = new PriorityQueue<>(5, (a,b) ->  a.x*matrix.length+a.y - b.x*matrix.length-b.y);
        if (i>0 && visited[i-1][j]==0 && Math.abs(matrix[i-1][j]-matrix[i][j])<=1)
            pq.add(new Point(i-1,j));
        if (j>0 && visited[i][j-1]==0 && Math.abs(matrix[i][j-1]-matrix[i][j])<=1)
            pq.add(new Point(i,j-1));
        if (i<matrix.length-1 && visited[i+1][j]==0 && Math.abs(matrix[i+1][j]-matrix[i][j])<=1)
            pq.add(new Point(i+1,j));
        if (j<matrix[0].length-1 && visited[i][j+1]==0 && Math.abs(matrix[i][j+1]-matrix[i][j])<=1)
            pq.add(new Point(i,j+1));
        return pq;
    }
    /**
     * Main alghoritm for DFS, traverses the labyrinth according to DFS.
     * @return first visited nodes, then length of path, then path itself, then cost of path.
     */
    public String run(){
        //Stack for open list.
        Stack<Pair> stack = new Stack<>();
        //adds the start node and came from information which is (-1,-1).
        stack.push(new Pair(start,cameFrom[start.x][start.y]));
        //holds the last visited node.
        Point lastVisited = start;
        //until openlist is empty or goal node is reached loops continue.
        while (!stack.empty()){
            //pair of current node and came from information of that node.
            Pair pair = stack.pop();
            // current node
            Point currNode = pair.node;
            // update lastvisited
            lastVisited = currNode;
            // current came from
            Point currCame = pair.cameFrom;
            //checks whether current node is visited or not.
            if (visited[currNode.x][currNode.y]==0){
                //if came from information is not there updates this otherwise does not update
                if (cameFrom[currNode.x][currNode.y].x == -1 &&  cameFrom[currNode.x][currNode.y].y == -1)
                    cameFrom[currNode.x][currNode.y] = currCame;
                // updates the visited information of current node.
                visited[currNode.x][currNode.y] = 1;
                if (currNode.x == goal.x && currNode.y == goal.y){
                    break;
                }
                // priority queus of neignbour nodes.
                PriorityQueue<Point> pq = getNeighbours(currNode.x, currNode.y);
                while (!pq.isEmpty()) {
                    Point p = pq.remove();
                    if (cameFrom[p.x][p.y].x == -1 && cameFrom[p.x][p.y].y==-1)
                        cameFrom[p.x][p.y] = currNode;
                    stack.push(new Pair(p, currNode));
                }
            }
        }
        //output string
        String s="";

        for (int i=0 ; i<visited.length; i++){
            for (int j=0; j<visited[0].length; j++){
                s += visited[i][j] + " ";
            }
            s+="\n";
        }
        //path length and path of traverse.
        int n=2;
        String s1 = lastVisited.x+" "+lastVisited.y;
        Point p = cameFrom[lastVisited.x][lastVisited.y];
        //total distance along the path which is euclidean distance.
        double distance = Math.sqrt(
                Math.pow(lastVisited.x-p.x,2)+
                        Math.pow(lastVisited.y-p.y,2)+
                        Math.pow(matrix[lastVisited.x][lastVisited.y]-matrix[p.x][p.y],2)
        );
        while (!(p.x==start.x && p.y==start.y)){
            n++;
            s1 = p.x + " " + p.y + "\n"+ s1;
            distance+= Math.sqrt(
                    Math.pow(cameFrom[p.x][p.y].x-p.x,2)+
                            Math.pow(cameFrom[p.x][p.y].y-p.y,2)+
                            Math.pow(matrix[cameFrom[p.x][p.y].x][cameFrom[p.x][p.y].y]-matrix[p.x][p.y],2));
            p = cameFrom[p.x][p.y];
        }
        s1= start.x +" "+ start.y +"\n" + s1;
        String out = s+n+"\n"+s1 + "\n" + String.format("%.2f", distance);
        return out;

    }
}
