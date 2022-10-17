//Jarod Gomberg
//March 25, 2022
//COP3503
//Project 4 - maze.java


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

/*
    Given the size and contents of the maze, figure out the fewest number of moves Sastry needs to
        make to get out of the maze.

    If Sastry can get out of the maze, output a single integer representing the fewest number of moves
        it will take him to get out. If he can't get out, output -1.
*/

public class maze {

    //declare necessary initial variables
    public static char[][] maze;
    public static int squares;
    public static boolean[] visited;

    public static void main(String[] args) {
        @SuppressWarnings("unchecked")

        //scan input for size of maze variables
        Scanner stdin = new Scanner(System.in);
        int rows = stdin.nextInt();
        int cols = stdin.nextInt();

        /*
            get number of squares for maze
            equal to size of maze
            add additional squares for every letter of alphabet
            these will be teleporters
        */
        int size = rows * cols;
        squares = 26 + size;
        char maze[][] = new char[rows][];

        //read the spaces to the maze
        for(int i = 0; i < rows; i++) {
            maze[i] = stdin.next().toCharArray();
        }
  
        //make a list of lists for edges in the path 
        @SuppressWarnings("unchecked") 
        ArrayList<Edge> path[] = new ArrayList[squares];
        for(int i = 0; i < squares; i++) {
            path[i] = new ArrayList<>();
        }

        //variables for important locations
        int curr = 0;
        int exit = 0;

        /*
            Now go through the maze and process the spaces.
            Look for start (curr position) and the exit.
            Read all walls and valid squares for the path out
        */
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                int source = i * cols + j;

                
                //if the current location in maze is Sastry's location 
                //get it and store it
                if(maze[i][j] == '*') {
                    curr = source;
                }

                //Found the exit, so store it
                if(maze[i][j] == '$') {
                    exit = source;
                }
                
                //if not wall, add edge for valid path and adj squares
                if(maze[i][j] != '!') {
                    if(i + 1 < rows && maze[i + 1][j] != '!') {
                        int dest = (i+1)*cols + j;

                        path[source].add(new Edge(dest, 1));
                        path[dest].add(new Edge(source, 1));
                    }
                    if(j + 1 < cols && maze[i][j+1] != '!') {
                        int dest = i*cols+j+1;

                        path[source].add(new Edge(dest, 1));
                        path[dest].add(new Edge(source, 1));
                    }
                }
            }  
        }

        /*
            Now handle teleport squares.
            Check if square holds a letter and add the edges to the path
            for corresponding teleportation
        */

        int dest = size;
        for(char tele = 'A'; tele <= 'Z'; tele++, dest++) {
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < cols; j++) {
                    int source = i*cols+j;

                    if(maze[i][j] == tele) {
                        path[source].add(new Edge(dest, 1));
                        path[dest].add(new Edge(source, 0));
                    }
                }
            }
        }

        //Declare variables for bfs and obtaining shortest path
        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
        queue.add(curr);

        //initialize the distance array and keep track of visited squares
        int dist[] = new int[squares];
        visited = new boolean[squares];
        visited[curr] = true;

       /*
            check the distance and update accordingly
            make sure to mark visited squares so we continue to process new
            and know we located and reached exit
       */
        while(queue.size() != 0) {
            int v = queue.poll();
            for(Edge e : path[v]) {
                if(!visited[e.s]) {
                    visited[e.s] = true;
                    dist[e.s] = dist[v] + e.weight;

                    /*
                        Check weight of edges and add to queue accordingly.
                        Put weight 1 in back and 0 before, to maintain increasing order
                    */
                    if(e.weight == 0) {
                        queue.addFirst(e.s);
                    }
                    else {
                        queue.add(e.s);
                    }
                }
            }
        }

        //print to output specifications, getting shortest path or -1 if impossible
        if(visited[exit]) {
            System.out.println(dist[exit]);
        }
        else {
            System.out.println("-1");
        }
    }
}

//Edge class for edge object
class Edge {
    int s; 
    int weight;

    public Edge(int s, int weight) {
        this.s = s;
        this.weight = weight;
    }
}