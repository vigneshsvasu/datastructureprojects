import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;
import java.util.ArrayList;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */



    HashMap<String, ArrayList<String>> adjacencyList = new HashMap<>();
    HashMap<String, Node> allNodes = new HashMap<>();

    void addNode (Node n) {
        this.allNodes.put(n.getId(), n);
    }


    void addEdge(Edge e){
        if (!adjacencyList.containsKey(e.v1)) {
            ArrayList<String> newArr = new ArrayList<>();
            newArr.add(e.v2);
            adjacencyList.put(e.v1, newArr);
        }
        else {
            ArrayList<String> newArr = adjacencyList.get(e.v1);
            newArr.add(e.v2);
            adjacencyList.put(e.v1, newArr);
        }
        if (!adjacencyList.containsKey(e.v2)) {
            ArrayList<String> newArr = new ArrayList<>();
            newArr.add(e.v1);
            adjacencyList.put(e.v2, newArr);
        }
        else {
            ArrayList<String> newArr = adjacencyList.get(e.v2);
            newArr.add(e.v1);
            adjacencyList.put(e.v2, newArr);
        }
    }

    static class Edge {
        String v1;
        String v2;

        Edge(String x, String y) {
            v1 = x;
            v2 = y;
        }
    }

    static class Node {
        String id;
        double lat;
        double lon;
        HashMap<String, String> tags = new HashMap<String, String>();
        double priority;

        Node(String pass, double x, double y) {
            this.id = pass;
            this.lat = y;
            this.lon = x;
        }

        public Node(String pass, double x, double y, HashMap tag) {
            this.id = pass;
            this.lat = y;
            this.lon = x;
            this.tags = tag;
        }

        public String getId() {
            return this.id;
        }

        public void addTag(String x, String y) {
            tags.put(x,y);
        }

        public double getLat(){
            return this.lat;
        }

        public double getLon(){
            return this.lon;
        }

        public double getPriority() {
            return priority;
        }

        public void setPriority(double val) {
            priority = val;
        }
    }

    static class Way {
        int id;
        ArrayList<String> refs;
        HashMap<String, String> tags = new HashMap<>();
        ArrayList<Edge> possibleEdges = new ArrayList<>();
        String lastId;
        boolean valid = false;

        public ArrayList<Edge> getEdges() {
            return possibleEdges;
        }
        public Way (int id) {
            this.id = id;
            refs = new ArrayList<>();
            tags = new HashMap();
        }

        public void addRef(String id) {
            refs.add(id);
        }

        public void addTag(String x, String y) {
            tags.put(x,y);
        }

        public void setLastId(String id) {
            this.lastId = id;
            addRef(id);
        }

        public void setValid() {
            valid = true;
        }

        public void connectAll() {
            if (valid) {
                for (int i = 0; i < refs.size() - 1; i++) {
                    Edge newEdge = new Edge(refs.get(i), refs.get(i + 1));
                    possibleEdges.add(newEdge);
                }
            }
        }
    }


    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputFile, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // TODO: Your code here.
        ArrayList<String> removeList = new ArrayList<>();
        for (String x: allNodes.keySet()) {
            if (adjacencyList.get(x) == null) {
                removeList.add(x);
            }
        }
        for (String y: removeList) {
            allNodes.remove(y);
        }
    }

    /** Returns an iterable of all vertex IDs in the graph. */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        ArrayList<Long> verticeList = new ArrayList<>();
        for (String x: allNodes.keySet()) {
            verticeList.add(new Long(x));
        }
        return verticeList;
    }


    /** Returns ids of all vertices adjacent to v. */
    Iterable<Long> adjacent(long v) {
        String x = Long.toString(v);
        ArrayList<Long> adjList = new ArrayList<>();
        for (String y: adjacencyList.get(x)) {
            adjList.add(new Long(y));
        }
        return adjList;
    }

    /** Returns the distance in units of longitude between vertices v and w. */
    double distance(long v, long w) {
        String x = Long.toString(v);
        String y = Long.toString(w);
        double xLon = allNodes.get(x).getLon();
        double yLon = allNodes.get(y).getLon();
        double xLat = allNodes.get(x).getLat();
        double yLat = allNodes.get(y).getLat();
        double answer = Math.sqrt((xLon - yLon) * (xLon - yLon) + (xLat - yLat) * (xLat - yLat));
        return answer;
    }

    /** Returns the Euclidean distance between vertices v and w, where Euclidean distance
     *  is defined as sqrt( (lonV - lonV)^2 + (latV - latV)^2 ). */

    /** Returns the vertex id closest to the given longitude and latitude. */
    long closest(double lon, double lat) {
        long val = new Long(0);
        double min = 10000000;
        for (String x: allNodes.keySet()) {
            double diffLon = Math.abs(allNodes.get(x).getLon() - lon);
            double diffLat = Math.abs(allNodes.get(x).getLat() - lat);
            double total = Math.sqrt(diffLon * diffLon + diffLat * diffLat);
            if (total < min) {
                val = new Long(x);
                min = total;
            }
        }
        return val;
    }

    /** Longitude of vertex v. */
    double lon(long v) {
        String x = Long.toString(v);
        return allNodes.get(x).getLon();
    }

    /** Latitude of vertex v. */
    double lat(long v) {
        String x = Long.toString(v);
        return allNodes.get(x).getLat();
    }
}
