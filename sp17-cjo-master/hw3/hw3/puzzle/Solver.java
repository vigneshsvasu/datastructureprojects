package hw3.puzzle;


import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;

/**
 * Created by vigneshvasu on 3/22/17.
 */
public class Solver {


    private class Node implements Comparable<Node> {
        WorldState ws;
        int numMoves;
        Node nextNode;

        Node(WorldState x, int moves) {
            ws = x;
            numMoves = moves;
            nextNode = this;
        }

        Node(WorldState x, int moves, Node curr) {
            ws = x;
            numMoves = moves;
            nextNode = curr;
        }

        @Override
        public int compareTo(Node y) {
            int x = this.ws.estimatedDistanceToGoal();
            int m = y.ws.estimatedDistanceToGoal();
            if (x > m) {
                return 1;
            } else if (x < m) {
                return -1;
            }
            return 0;
        }


    }

    Iterable<WorldState> worldStates;
    WorldState start;
    int totalMoves;
    MinPQ<Node> pq;
    ArrayList<WorldState> allMoves;

    public Solver(WorldState initial) {
        start = initial;
        pq = new MinPQ<Node>();
        totalMoves = 0;
        allMoves = new ArrayList<WorldState>();
        worldStates = solution();
    }

    public int moves() {
        return totalMoves;
    }

    public Iterable<WorldState> solution() {
        if (pq.isEmpty() && start.isGoal()) {
            totalMoves = 0;
            allMoves.add(start);
            return allMoves;
        }
        if (!pq.isEmpty()) {
            if (pq.min().ws.isGoal()) {
                return allMoves;
            }
        }
        allMoves.add(start);
        Iterable<WorldState> neighborArray = start.neighbors();
        Node startNode = new Node(start, totalMoves);
        pq.insert(startNode);
        for (WorldState x : neighborArray) {
            int theseMoves = totalMoves + 1;
            pq.insert(new Node(x, theseMoves, startNode));
        }
        while (!pq.min().ws.isGoal()) {
            Node lastMove = pq.delMin();
            allMoves.add(lastMove.ws);
            System.out.println(lastMove.ws.toString());
            totalMoves++;
            Iterable<WorldState> otherArray = lastMove.ws.neighbors();
            for (WorldState h : otherArray) {
                boolean rand = false;
                for (WorldState o: allMoves) {
                    if (h.equals(o)) {
                        rand = true;
                    }
                }
                if (rand) {
                    continue;
                }
                if (h.equals(lastMove.nextNode.ws)) {
                    continue;
                }
                int otherMoves = totalMoves + 1;
                pq.insert(new Node(h, otherMoves, lastMove));
            }
        }
        allMoves.add(pq.min().ws);
        totalMoves++;
        return allMoves;
    }
}
