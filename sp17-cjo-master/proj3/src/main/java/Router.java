import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a LinkedList of <code>Long</code>s representing the shortest path from st to dest, 
     * where the longs are node IDs.
     */

    public static LinkedList<Long> shortestPath(GraphDB g, double stlon, double stlat, double destlon, double destlat) {
        GraphDB.Node startNode = g.allNodes.get(Long.toString(g.closest(stlon, stlat)));
        GraphDB.Node endNode = g.allNodes.get(Long.toString(g.closest(destlon, destlat)));
        Comparator<GraphDB.Node> x = new NodeComparator();
        PriorityQueue<GraphDB.Node> newQueue = new PriorityQueue<GraphDB.Node>(g.allNodes.size(), x);
        HashMap<String, Double> distMap = new HashMap<>();
        HashMap<String, String> parents = new HashMap<>();
        LinkedList<Long> newPath = new LinkedList<>();
        newPath.add(new Long(startNode.getId()));
        newQueue.add(startNode);
        GraphDB.Node v = newQueue.poll();
        while (newQueue.peek() != null && v != endNode) {
            for (String j: g.adjacencyList.get(v)) {
                if (distMap.get(j) == null
                        || distMap.get(v) + g.distance(new Long(v.getId()), new Long(j)) < distMap.get(j)) {
                    double bestDist = distMap.get(v) + g.distance(new Long(v.getId()), new Long(j));
                    if (distMap.get(j) == null) {
                        distMap.put(j, bestDist);
                    }
                    else {
                        distMap.replace(j, bestDist);
                    }
                    g.allNodes.get(j).setPriority(bestDist + g.distance(new Long(j), new Long(endNode.getId())));
                    parents.put(j, v.getId());
                    newPath.add(new Long(j));
                    newQueue.add(g.allNodes.get(j));
                }
            }
            v = newQueue.poll();
        }

    return newPath;
    }

    static class NodeComparator implements Comparator<GraphDB.Node>
    {
        public int compare(GraphDB.Node c1, GraphDB.Node c2)
        {
            Double a1 = c1.getPriority();
            Double a2 = c2.getPriority();
            if (a1 > a2) {
                return 1;
            }
            else if (a1 < a2) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    /*
    public static LinkedList<Long> shortestPath(GraphDB g, double stlon, double stlat, double destlon, double destlat) {
        GraphDB.Node startNode = g.allNodes.get(Long.toString(g.closest(stlon, stlat)));
        GraphDB.Node endNode = g.allNodes.get(Long.toString(g.closest(destlon, destlat)));
        ArrayList<String> settledNodes = new ArrayList<>();
        ArrayList<String> unsettledNodes = new ArrayList<>();
        HashMap<String,Double> distMap = new HashMap<String, Double>();
        HashMap<String, String> pred = new HashMap<>();
        for (String s: g.allNodes.keySet()) {
            distMap.put(s, Double.MAX_VALUE);
        }
        distMap.replace(startNode.getId(), 0.);
        unsettledNodes.add(startNode.getId());
        while (!unsettledNodes.isEmpty()) {
            String evalNode = getNodeWithLowestDistance(g, endNode.getId(), unsettledNodes);
            unsettledNodes.remove(evalNode);
            settledNodes.add(evalNode);
            for (String s: g.adjacencyList.get(evalNode)) {
                if (!settledNodes.contains(s)) {
                    double dist = g.distance(new Long(endNode.getId()), new Long(s));
                    double newDist = distMap.get(evalNode) + dist;
                    if (distMap.get(s) > newDist) {
                        distMap.replace(s, newDist);
                        pred.put(s, evalNode);
                        unsettledNodes.add(s);
                    }
                }
            }
        }
        LinkedList<Long> path = new LinkedList<>();
        String step = endNode.getId();
        path.add(new Long(step));
        while (pred.get(step) != null) {
            step = pred.get(step);
            path.add(new Long(step));
        }
        Collections.reverse(path);
        return path;
    }


    static String getNodeWithLowestDistance (GraphDB g, String s, ArrayList<String> unsettled) {
        String y = "";
        double minVal = Double.MAX_VALUE;
        for (String x: unsettled) {
            double dist = g.distance(new Long(s), new Long(x));
            if (dist < minVal) {
                y = x;
                minVal = dist;
            }
        }
        return y;

    }

    */
}
