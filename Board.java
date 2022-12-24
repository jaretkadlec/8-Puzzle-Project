import edu.princeton.cs.algs4.Stack;
public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] board;
    private int[][] goal;
    public Board(int[][] tiles) {
        board = tiles;
        int n = board[0].length;
        goal = new int[n][n];
        int pos = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1) {
                    goal[i][j] = 0;
                } else {
                    goal[i][j] = pos;
                    pos += 1;
                }
            }
        }
    }
                                           
    // string representation of this board
    public String toString() {
        String str = Integer.toString(dimension());
        str += "\n";
        for (int i = 0; i < dimension(); i++) {
            String row = "";
            for (int j = 0; j < dimension(); j++) {
                String cell = Integer.toString(board[i][j]);
                row += cell;
                row += "  ";
            }
            str += row;
            str += "\n";
        }
        return str;
    }

    // board dimension n
    public int dimension() {
        return board[0].length;
    }

    // number of tiles out of place
    public int hamming() {
        int pos = 1;
        int ham = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] != pos && board[i][j] != 0) {
                    ham += 1;
                }
                pos += 1;
            }
        } 
        return ham;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int n = dimension();
        int mdist = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = board[i][j];
                if (val != 0) {
                    int targetRow = (val - 1) / n;
                    int targetCol = (val - 1) % n;
                    int rowDist = Math.abs(i - targetRow);
                    int colDist = Math.abs(j - targetCol);
                    int dist = rowDist + colDist;
                    mdist += dist;
                }
            }
        }
        return mdist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // StdOut.println("isGoal function called");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                // StdOut.println(board[i][j] + " - " + goal[i][j]);
                if (board[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        // StdOut.println("Equals function called");
        Board yb = (Board) y;
        int[][] Y = yb.board;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                // StdOut.println(board[i][j] + " - " + Y[i][j]);
                if (board[i][j] != Y[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boardStack = new Stack<Board>();
        int n = dimension();
        int[][] neighborBoard1 = new int[n][n];
        int[][] neighborBoard2 = new int[n][n];
        int[][] neighborBoard3 = new int[n][n];
        int[][] neighborBoard4 = new int[n][n];
        for (int i = 0; i < n; i++) {
            neighborBoard1[i] = board[i].clone();
            neighborBoard2[i] = board[i].clone();
            neighborBoard3[i] = board[i].clone();
            neighborBoard4[i] = board[i].clone();
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    if (i - 1 >= 0) {
                        // StdOut.println("Adding neighbor");
                        int topVal = neighborBoard1[i-1][j];
                        neighborBoard1[i-1][j] = 0;
                        neighborBoard1[i][j] = topVal;
                        Board b1 = new Board(neighborBoard1);
                        boardStack.push(b1);
                    }
                    if (i + 1 < n) {
                        // StdOut.println("Adding neighbor");
                        int bottomVal = neighborBoard2[i+1][j];
                        neighborBoard2[i+1][j] = 0;
                        neighborBoard2[i][j] = bottomVal;
                        Board b2 = new Board(neighborBoard2);
                        boardStack.push(b2);
                    }  
                    if (j - 1 >= 0) {
                        // StdOut.println("Adding neighbor");
                        int rightVal = neighborBoard3[i][j-1];
                        neighborBoard3[i][j-1] = 0;
                        neighborBoard3[i][j] = rightVal;
                        Board b3 = new Board(neighborBoard3);
                        boardStack.push(b3);
                    }
                    if (j + 1 < n) {
                        // StdOut.println("Adding neighbor");
                        int leftVal = neighborBoard4[i][j+1];
                        neighborBoard4[i][j+1] = 0;
                        neighborBoard4[i][j] = leftVal;
                        Board b4 = new Board(neighborBoard4);
                        boardStack.push(b4);
                    }
                }
            }
        }
        // StdOut.println("Stack size: " + boardStack.size());
        return boardStack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int n = dimension();
        int i1 = 0;
        int i2 = 0;
        int j1 = 0;
        int j2 = 1;
        if (board[i1][j1] == 0) {
            i1 += 1;
        }
        if (board[i2][j2] == 0) {
            i2 += 1;
        }
        int[][] twinBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            twinBoard[i] = board[i].clone();
        }
        // StdOut.println(twinBoard[i1][j1] + " - " + twinBoard[i2][j2]);
        int temp = twinBoard[i1][j1];
        twinBoard[i1][j1] = twinBoard[i2][j2];
        twinBoard[i2][j2] = temp;
        // StdOut.println(twinBoard[i1][j1] + " - " + twinBoard[i2][j2]);
        Board twinBoardRet = new Board(twinBoard);
        return twinBoardRet;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++)
        //         tiles[i][j] = in.readInt();
        // Board initial = new Board(tiles);
        // Iterable<Board> neighboring = initial.neighbors();
        // StdOut.println(initial.toString());
        // for (Board b: neighboring) {
        //     StdOut.println(b.toString());
        // }
    }

}