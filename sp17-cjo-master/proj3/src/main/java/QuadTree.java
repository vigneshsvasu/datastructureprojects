import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
/**
 * Created by vigneshvasu on 4/18/17.
 * much help from princeton quadTree
 */
    public class QuadTree  {
        private Node root;
        public int count = 0;

        // helper node data type
        private class Node {
            Double x, y;            // x- and y- coordinates
            Double a, b;
            Node NW, NE, SE, SW;   // four subtrees
            String value;           // associated data
            int depth;
            double londPP;

            Node(Double x, Double y, Double a, Double b, String value, int dep) {
                this.x = x;
                this.y = y;
                this.a = a;
                this.b = b;
                this.value = value;
                int depth = dep;
                londPP = (this.b - this.y)/ (256);
            }

            public void setDepth(int x) {
                depth = x;
            }

            public int getDepth() {
                return depth;
            }

            public double getX() {
                return this.x;
            }

            public double getY() {
                return this.y;
            }

            public double getA() {
                return this.a;
            }

            public double getB() {
                return this.b;
            }

            public String toString() {
                return x + " " + y + " " + a + " " + b + " " + londPP + " " + depth + " " + value;
            }

            public boolean intersectsTile(double upLat, double upLon, double botLat, double botLon) {
                if (this.x < botLat || this.y > botLon || this.a > upLat || this.b < upLon) {
                    return false;
                }
                return true;
            }

            public boolean checkLonDPP(double pp) {
                if (this.londPP > pp) {
                    return false;
                }
                return true;

            }

            public String getValue() {
                return this.value;
            }

            public Node goNW() {
                return this.NW;
            }

            public Node goNE() {
                return this.NE;
            }
            public Node goSW() {
                return this.SW;
            }
            public Node goSE() {
                return this.SE;
            }

        }

        public QuadTree(Double x, Double y, Double a, Double b, String v) {
            this.root = new Node(x, y, a, b, v, 7);
            this.root.setDepth(0);
            double halfLength = (x.doubleValue() + a.doubleValue()) / 2;
            double halfHeight = (y.doubleValue() + b.doubleValue()) / 2;
            insert(this.root, x, y, halfLength, halfHeight, v + "1");
            insert(this.root, halfLength, y, a, halfHeight, v + "3");
            insert(this.root, x, halfHeight, halfLength, b, v + "2");
            insert(this.root, halfLength, halfHeight, a, b, v + "4");
        }

        public Node getRoot() {
            return this.root;
        }

        public void insert (Node seed, Double x, Double y, Double a, Double b, String v) {
            double halfLength = (seed.x.doubleValue() + seed.a.doubleValue()) / 2;
            double halfHeight = (seed.y.doubleValue() + seed.b.doubleValue()) / 2;
            double hL = (x.doubleValue() + a.doubleValue()) / 2;
            double hH = (y.doubleValue() + b.doubleValue()) / 2;
            if (seed.getDepth() >= 7) {
                seed.NE = null;
                seed.NW = null;
                seed.SE = null;
                seed.SW = null;
                return;
            }
            else if (a == halfLength && b == halfHeight) {
                seed.NW = new Node(x, y, a, b, v, 0);
                seed.NW.setDepth(seed.getDepth() + 1);
                insert(seed.NW, x, y, hL, hH, v + "1");
                insert(seed.NW, hL, y, a, hH, v + "3");
                insert(seed.NW, x, hH, hL, b, v + "2");
                insert(seed.NW, hL, hH, a, b, v + "4");
            }
            else if (x == halfLength && b == halfHeight) {
                seed.NE = new Node(x, y, a, b, v, 0);
                seed.NE.setDepth(seed.getDepth() + 1);
                insert(seed.NE, x, y, hL, hH, v + "1");
                insert(seed.NE, hL, y, a, hH, v + "3");
                insert(seed.NE, x, hH, hL, b, v + "2");
                insert(seed.NE, hL, hH, a, b, v + "4");
            }
            else if (a == halfLength && y == halfHeight) {
                seed.SW = new Node(x, y, a, b, v, 0);
                seed.SW.setDepth(seed.getDepth() + 1);
                insert(seed.SW, x, y, hL, hH, v + "1");
                insert(seed.SW, hL, y, a, hH, v + "3");
                insert(seed.SW, x, hH, hL, b, v + "2");
                insert(seed.SW, hL, hH, a, b, v + "4");
            }
            else if (x == halfLength && y == halfHeight) {
                seed.SE = new Node(x, y, a, b, v, 0);
                seed.SE.setDepth(seed.getDepth() + 1);
                insert(seed.SE, x, y, hL, hH, v + "1");
                insert(seed.SE, hL, y, a, hH, v + "3");
                insert(seed.SE, x, hH, hL, b, v + "2");
                insert(seed.SE, hL, hH, a, b, v + "4");
            }
        }


        ArrayList<Node> oResults = new ArrayList<>();
        public void search(Node start, double upLat, double upLon, double botLat, double botLon, double lonDPP) {
            if (start.checkLonDPP(lonDPP) && start.intersectsTile(upLat, upLon, botLat, botLon)) {
                oResults.add(start);
                return;
            }
            else if (!start.checkLonDPP(lonDPP) && start.intersectsTile(upLat, upLon, botLat, botLon)) {
                if (start.goNE() != null) {
                    search(start.goNE(), upLat, upLon, botLat, botLon, lonDPP);
                }
                if (start.goNW() != null) {
                    search(start.goNW(), upLat, upLon, botLat, botLon, lonDPP);
                }
                if (start.goSW() != null) {
                    search(start.goSW(), upLat, upLon, botLat, botLon, lonDPP);
                }
                if (start.goSE() != null) {
                    search(start.goSE(), upLat, upLon, botLat, botLon, lonDPP);
                }
            }
        }

        public void nullReturnMapMethod(Node start, double upLat, double upLon, double botLat, double botLon, double lonDPP) {
            if (start.intersectsTile(upLat, upLon, botLat, botLon) && start.getDepth() == 7) {
                oResults.add(start);
                return;
            }
            else if (!start.checkLonDPP(lonDPP) && start.intersectsTile(upLat, upLon, botLat, botLon)) {
                if (start.goNE() != null) {
                    nullReturnMapMethod(start.goNE(), upLat, upLon, botLat, botLon, lonDPP);
                }
                if (start.goNW() != null) {
                    nullReturnMapMethod(start.goNW(), upLat, upLon, botLat, botLon, lonDPP);
                }
                if (start.goSW() != null) {
                    nullReturnMapMethod(start.goSW(), upLat, upLon, botLat, botLon, lonDPP);
                }
                if (start.goSE() != null) {
                    nullReturnMapMethod(start.goSE(), upLat, upLon, botLat, botLon, lonDPP);
                }
            }
        }

        public Map<String, Object> learningSucks(Node start, double upLat, double upLon, double botLat, double botLon, double lonDPP) {
            search(start,upLat, upLon, botLat, botLon, lonDPP);
            ArrayList<Node> results = oResults;
            if (results.size() == 0) {
                nullReturnMapMethod(start, upLat, upLon, botLat, botLon, lonDPP);
            }
            int row = 0;
            int col = 0;
            results = oResults;
            Node test = results.get(0);
            for (Node h : results) {
                if (test.getY() == h.getY()) {
                    row ++;
                }
                if (test.getX() == h.getX()) {
                    col ++;
                }
            }
            if (row == 0) {
                row = 1;
                col = 1;
            }
            results = sortListbyLon(results, row, col);
            int count = 0;
            String[][] stringArr = new String[row][col];
            double val = 0.0;
            double val1 = 0.0;
            double val2 = 0.0;
            double val3 = 0.0;
            int sumCount = 0;
            /*
            for (int k = 0; k < row; k ++ ){
                Node testVal = results.get(sumCount);
                for (int j = 0; j < col; j ++) {
                    int num = 1;
                    Node otherTest = results.get(num);
                    while (testVal.getY() != otherTest.getY()) {
                        if (num >= results.size()) {;
                            break;
                        }
                        num = num + 1;
                        otherTest = results.get(num);
                    }
                    if (num < results.size() && num != 0) {
                        stringArr[k][j] = otherTest.getValue();
                        sumCount++;
                        if (row == 1 && col == 1) {
                            stringArr[k][j] = "root.png";
                        }
                        if (k == 0 && j == 0) {
                            val = results.get(count).getX();
                            val1 = results.get(count).getY();
                        }
                        if (k == row - 1 && j == col - 1) {
                            val2 = results.get(count).getX();
                            val3 = results.get(count).getY();
                        }
                    }
                }
            }*/
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    stringArr[i][j] = results.get(count).getValue();
                    if (row == 1 && col == 1) {
                        stringArr[i][j] = "root.png";
                    }
                    if (i == 0 && j == 0) {
                        val = results.get(count).getX();
                        val1 = results.get(count).getY();
                    }
                    if (i == row - 1 && j == col - 1) {
                        val2 = results.get(count).getA();
                        val3 = results.get(count).getB();
                    }
                    count++;
                }
            }
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("grid", stringArr);
            returnMap.put("ullon", val1);
            returnMap.put("ullat", val);
            returnMap.put("lrlon", val3);
            returnMap.put("lrlat", val2);
            returnMap.put("depth", results.get(0).getDepth());
            return returnMap;
        }

        public ArrayList<Node> sortListbyLon (ArrayList<Node> x, int v, int y) {
            ArrayList<ArrayList<Node>> answer = new ArrayList<>();
            for (int i = 0; i < v; i ++) {
                ArrayList<Node> newArr = new ArrayList<>();
                double min = -100000000;
                int count = 0;
                for (int k = 0; k < x.size(); k++) {
                    if (x.get(k).getX() > min) {
                        min = x.get(k).getX();
                    }
                }
                for (int j = 0; j < x.size(); j ++) {
                    if (x.get(count).getX() == min) {
                        newArr.add(x.get(count));
                        x.remove(count);
                        j = j - 1;
                    }
                    else {
                        count++;
                    }
                }
                answer.add(newArr);
            }
            ArrayList<Node> result = new ArrayList<>();
            for (int u = 0 ; u < answer.size(); u++) {
                double min = 100000000;
                int crap = 0;
                while (answer.get(u).size() != 0) {
                    crap = 0;
                    min = answer.get(u).get(crap).getY();
                    int count = 0;
                    while (crap < y) {
                        if (min > answer.get(u).get(count).getY()) {
                            min = answer.get(u).get(count).getY();
                            crap++;
                            count++;
                        }
                        else {
                            crap++;
                        }
                    }
                    result.add(answer.get(u).get(count));
                    answer.get(u).remove(count);
                }
            }
            return result;
        }

        public static void main(String[] args) {
            QuadTree newTree = new QuadTree(37.892195547244356, -122.2998046875, 37.82280243352756, -122.2119140625,  "");
            Map<String, Object> test = newTree.learningSucks(newTree.getRoot(), 37.87655856892288, -122.24163047377972, 37.87548268822065, -122.24053369025242, 0.0000012957 );
            System.out.println(test);
            String[][] fuck = (String[][]) test.get("grid");
            for (int i = 0; i < fuck.length; i ++) {
                for (int j = 0; j < fuck[0].length; j++) {
                    System.out.println(fuck[i][j]);
                }
            }
        }
    }
