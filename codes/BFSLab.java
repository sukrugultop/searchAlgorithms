
import java.awt.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
//BFS algorithm for labyrinth problem
public class BFSLab {
    //main input of problem(should contains 1s and 0s. 1s for walls 0s for allowed paths.)
    int[][] matrix;
    //hold the information about whether corresponding cell is visited or not.
    int[][] visited;
    //for the final path each cell holds the information where it is came from.
    Point[][] cameFrom;
    // start and goal points of the
    Point start, goal;

    /**
     * Initializes the BFS for labyrinth.
     * @param matrix 2D labyrinth representation
     * @param start start point of BFS.
     * @param goal goal point of BFS.
     */
    BFSLab(int[][] matrix, Point start, Point goal){
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
     *
     * @param i x coordinate of current point.
     * @param j y corrdinate of current point.
     * @return priority queue according to indexes of neighbours node.
     */
    public PriorityQueue<Point> getNeighbours(int i, int j){

        // initialize with size 5 and comparator for index.
        PriorityQueue<Point> pq = new PriorityQueue<>(5, (a,b) ->  b.x*matrix.length+b.y-a.x*matrix.length-a.y );
        if (i>0 && visited[i-1][j]==0 && matrix[i-1][j]!=1)
            pq.add(new Point(i-1,j));
        if (j>0 && visited[i][j-1]==0 && matrix[i][j-1]!=1)
            pq.add(new Point(i,j-1));
        if (i<matrix.length-1 && visited[i+1][j]==0 && matrix[i+1][j]!=1)
            pq.add(new Point(i+1,j));
        if (j<matrix[0].length-1 && visited[i][j+1]==0 && matrix[i][j+1]!=1)
            pq.add(new Point(i,j+1));
        return pq;
    }

    /**
     * Main alghoritm for BFS, traverses the labyrinth according to BFS.
     * @return first visited nodes, then length of path, then path itself, then cost of path.
     */
    public String run(){
        //Queue for open list(traverses the first element of list.)
        Queue<Pair> queue = new LinkedList<Pair>();
        //adds the start node and came from information which is (-1,-1).
        queue.add(new Pair(start,cameFrom[start.x][start.y]));
        //holds the last visited node.
        Point lastVisited = start;
        //until openlist is empty or goal node is reached loops continue.
        while (!queue.isEmpty()){
            //pair of current node and came from information of that node.
            Pair pair = queue.poll();
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
                // checks whether current node is goal node or not.
                if (currNode.x == goal.x && currNode.y == goal.y){
                    break;
                }
                // priority queus of neignbour nodes.
                PriorityQueue<Point> pq = getNeighbours(currNode.x, currNode.y);
                // adds the neighbours to open list.
                while (!pq.isEmpty()) {
                    Point p = pq.remove();
                    if (cameFrom[p.x][p.y].x == -1 && cameFrom[p.x][p.y].y==-1)
                        cameFrom[p.x][p.y] = currNode;
                    queue.add(new Pair(p, currNode));
                }
            }
        }

        //output string
        String s="";
        //visited information of labyrinth
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
        while (!(p.x==start.x && p.y==start.y)){
            n++;
            s1 = p.x + " " + p.y + "\n"+ s1;
            p = cameFrom[p.x][p.y];
        }
        s1= start.x +" "+ start.y +"\n" + s1;
        String out = s+n+"\n"+s1 + "\n" + (n-1)+".00";
        return out;

    }
}
