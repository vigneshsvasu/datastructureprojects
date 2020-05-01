package hw3.puzzle;

import edu.princeton.cs.algs4.Queue;
/* Created by Vignesh Vasu, neighbors method from Hug*/

public class Board implements WorldState {


    /** Returns the string representation of the board. 
      * Uncomment this method. */
    int[][] board;
    int[][] goal;
    public Board(int[][] tiles) {
        board = new int[tiles.length][tiles[0].length];
        for (int r = 0; r < tiles.length; r++) {
            for (int u = 0; u < tiles[0].length; u++) {
                board[r][u] = tiles[r][u];
            }
        }
        goal = new int[board.length][board[0].length];
        int count = 1;
        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j < goal[0].length; j++) {
                if (i == goal.length - 1 && j == goal[0].length - 1) {
                    goal[i][j] = 0;
                } else {
                    goal[i][j] = count;
                    count++;
                }
            }
        }
    }
    public int tileAt(int i, int j) throws IndexOutOfBoundsException {
        if (j < 0 || i < 0 || j > size() || i > size()) {
            throw new IndexOutOfBoundsException();
        }
        return board[i][j];
    }
    public int size() {
        return board.length;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != goal[i][j] && board[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        int total = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != goal[i][j]) {
                    int val = board[i][j];
                    for (int m = 0; m < goal.length; m++) {
                        for (int k = 0; k < goal[0].length; k++) {
                            if (goal[m][k] == val && val != 0) {
                                int row = m;
                                int col = k;
                                total = total + Math.abs(row - i) + Math.abs(col - j);
                            }
                        }
                    }
                }
            }
        }
        return total;
    }

    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }
    public int estimatedDistanceToGoal() {
        return manhattan();
    }
    public boolean isGoal() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean equals(Object y) {
        Board values = (Board) y;
        int[][] vals = values.board;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != vals[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();

    }

    public int hashCode() {
        return 0;
    }

    public static void main(String[] args) {
        int[][] test = new int[3][3];
        int count = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 2 && j == 2) {
                    test[i][j] = 0;
                } else {
                    test[i][j] = count;
                    count++;
                }
            }
        }
        int[][] otherTest = new int[3][3];
        otherTest[0][0] = 8;
        otherTest[0][1] = 1;
        otherTest[0][2] = 3;
        otherTest[1][0] = 4;
        otherTest[1][1] = 0;
        otherTest[1][2] = 2;
        otherTest[2][0] = 7;
        otherTest[2][1] = 6;
        otherTest[2][2] = 5;
        Board testBoard = new Board(test);
        Board otherTestBoard = new Board(otherTest);
        System.out.println(testBoard.toString());
        System.out.println(otherTestBoard.toString());
        System.out.println(new Board(otherTestBoard.goal).toString());
        System.out.println(otherTestBoard.isGoal());
        System.out.println(otherTestBoard.hamming());
        System.out.println(otherTestBoard.manhattan());


    }

}
