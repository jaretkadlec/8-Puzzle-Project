import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
public class Solver {

    private MinPQ<BoardNode> PQ;
    private MinPQ<BoardNode> twinPQ;
    private boolean goalFound = false;
    private boolean goalFoundTwin = false;
    private Board init;
    private class BoardNode implements Comparable<BoardNode> {
        private Board board;
        private int pf;
        private int numMoves;
        private BoardNode prev;
        public BoardNode(Board b, int m, BoardNode p) {
            this.board = b;
            this.prev = p;
            this.numMoves = m;
            this.pf = m + b.manhattan();
        }
        public int compareTo(BoardNode that) {
            return this.pf - that.pf;
        }
    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        init = initial;
        BoardNode initialBoard = new BoardNode(initial, 0, null);
        BoardNode initialBoardTwin = new BoardNode(initial.twin(), 0, null);
        PQ = new MinPQ<BoardNode>();
        twinPQ = new MinPQ<BoardNode>();
        PQ.insert(initialBoard);
        twinPQ.insert(initialBoardTwin);
        int iteration = 0;
        while (!goalFound && !goalFoundTwin) {
            iteration += 1;
            BoardNode dequedBoardNode = PQ.min();
            BoardNode dequedBoardTwinNode = twinPQ.min();
            Board dequedBoard = dequedBoardNode.board;
            Board dequedBoardTwin = dequedBoardTwinNode.board;
            // if (iteration < 5) {
            //     StdOut.println("Iteration: " + iteration);
            //     StdOut.println("Real" + dequedBoard.toString());
            //     StdOut.println("Twin" + dequedBoardTwin.toString());
            //     StdOut.println("-------------------------");
            // }
            if (dequedBoard.isGoal()) {
                // StdOut.println("hit breakpoint");
                goalFound = true;
            }
            else if (dequedBoardTwin.isGoal()) {
                // StdOut.println("hit breakpoint");
                goalFoundTwin = true;
            } else {
                PQ.delMin();
                twinPQ.delMin();
                Iterable<Board> boardIter = dequedBoard.neighbors();
                Iterable<Board> boardIterTwin = dequedBoardTwin.neighbors();
                for (Board b : boardIter) {
                    // StdOut.println("Adding neighbor");
                    if (dequedBoardNode.numMoves == 0) {
                        BoardNode bn = new BoardNode(b, dequedBoardNode.numMoves + 1, dequedBoardNode);
                        PQ.insert(bn);
                    }
                    else if (!b.equals(dequedBoardNode.prev.board)) {
                        BoardNode bn = new BoardNode(b, dequedBoardNode.numMoves + 1, dequedBoardNode);
                        PQ.insert(bn);
                    }
                }
                for (Board bt : boardIterTwin) {
                    // StdOut.println("Adding twin neighbor");
                    if (dequedBoardTwinNode.numMoves == 0) {
                        BoardNode btn = new BoardNode(bt, dequedBoardTwinNode.numMoves + 1, dequedBoardTwinNode);
                        twinPQ.insert(btn);
                    }
                    else if (!bt.equals(dequedBoardTwinNode.prev.board)) {
                        BoardNode btn = new BoardNode(bt, dequedBoardTwinNode.numMoves + 1, dequedBoardTwinNode);
                        twinPQ.insert(btn);
                    }
                }
            }
        }
    }
    
    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return goalFound;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        } 
        BoardNode minB = PQ.min();
        return minB.numMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        } 
        Stack<Board> sol = new Stack<Board>();
        BoardNode cur = PQ.min();
        while (cur.prev != null) {
            sol.push(cur.board);
            cur = cur.prev;
        }
        sol.push(init);
        return sol;
    }

    // test client (see below) 
    public static void main(String[] args) {

        // // create initial board from file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++)
        //         tiles[i][j] = in.readInt();
        // Board initial = new Board(tiles);

        // // solve the puzzle
        // // StdOut.println(initial.toString());
        // Solver solver = new Solver(initial);

        // // print solution to standard output
        // if (!solver.isSolvable())
        //     StdOut.println("No solution possible");
        // else {
        //     StdOut.println("Minimum number of moves = " + solver.moves());
        //     for (Board board : solver.solution())
        //         StdOut.println(board);
        // }
    }

}