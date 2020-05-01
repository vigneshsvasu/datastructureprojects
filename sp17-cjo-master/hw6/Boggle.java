

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.Comparator;
import java.io.File;
public class Boggle {
    int words;
    List<String> dictionary;
    char[][] boggleArray;
    ArrayList<String> prefArr;
    ArrayList<String> answerWords;
    int max;

    public Boggle(int words, List<String> dict, char[][] arr) {
        this.words = words;
        this.dictionary = dict;
        this.boggleArray = arr;
        answerWords = new ArrayList<>();
        prefArr = new ArrayList<>();
        max = Integer.MIN_VALUE;

        for (int i = 0; i < dictionary.size(); i++) {
            String hmm = dictionary.get(i).replaceAll("[^a-zA-Z]","");
            hmm = hmm.toLowerCase();
            dictionary.set(i, hmm);
            String s = "";
            for (int j = 1; j < dictionary.get(i).length() + 1; j++) {
                s = s + dictionary.get(i).substring(j-1,j);
                prefArr.add(s);
            }
            if (hmm.length() > max) {
                max = hmm.length();
            }
        }
    }

    class Node {
        char c;
        HashMap<Character, Node> children = new HashMap<Character, Node>();
        boolean isLeaf;

        public Node() {
        }

        public Node(char c){
            this.c = c;
        }

        public String toString() {
            return String.valueOf(c);
        }
    }



        public boolean isPrefix(String pref) {
            if (prefArr.contains(pref)) {
                return true;
            }
            return false;
        }


        public void searchWord(Node root, int row, int col) {
            if (isPrefix(root.toString())) {
                boolean[][] beenSearched = new boolean[boggleArray.length][boggleArray[0].length];
                beenSearched[row][col] = true;
                for (int i = 0; i < neighbors(row, col).size(); i++) {
                    String word = root.toString();
                    int[] y = neighbors(row, col).get(i);
                    char thisChar = boggleArray[y[0]][y[1]];
                    word = word + thisChar;
                    if (isPrefix(word)) {
                        Node x = new Node(thisChar);
                        beenSearched[y[0]][y[1]] = true;
                        searchWord(x, y[0], y[1], word, beenSearched);
                    }
                }
            }
        }

        public int[] findInBoggle(char x) {
            int[] dos = new int[2];
            for (int i = 0; i < boggleArray.length; i++) {
                for (int j = 0; j <boggleArray[0].length; j++) {
                    if (boggleArray[i][j] == x) {
                        dos[0] = i;
                        dos[1] = j;
                    }
                }
            }
            return dos;
        }

        public boolean searchWord(Node root, int row, int col, String word, boolean[][] beenSearched) {
            ArrayList<int[]> neighRay = neighbors(row, col);
            ArrayList<int[]> unSearched = new ArrayList<>();
            if (word.length() == 2) {
                char a = word.charAt(0);
                char b = word.charAt(1);
                int[] first = findInBoggle(a);
                int[] sec = findInBoggle(b);
                beenSearched = new boolean[boggleArray.length][boggleArray[0].length];
                beenSearched[first[0]][first[1]] = true;
                beenSearched[sec[0]][sec[1]] = true;
            }
            for (int i = 0; i < neighRay.size(); i++) {
                int[] point = neighRay.get(i);
                int rowVal = point[0];
                int colVal = point[1];
                if (!beenSearched[rowVal][colVal]) {
                    if (isPrefix(word + boggleArray[rowVal][colVal])) {
                        unSearched.add(point);
                    }
                }
            }
            if (unSearched.isEmpty()) {
                return false;
            }
            else {
                for (int j = 0; j < unSearched.size(); j++) {
                    int[] thisPoint = unSearched.get(j);
                    int rowV = thisPoint[0];
                    int colV = thisPoint[1];
                    beenSearched[rowV][colV] = true;
                    String thisW = word + boggleArray[rowV][colV];
                    if (isWord(thisW)) {
                        answerWords.add(thisW);
                        Node x = new Node(boggleArray[rowV][colV]);
                        searchWord(x, rowV, colV, thisW, beenSearched);
                    }
                    else if (isPrefix(thisW)) {
                        Node x = new Node(boggleArray[rowV][colV]);
                        searchWord(x, rowV, colV, thisW, beenSearched);
                    }
                    else {
                        return false;
                    }
                }
            }
            return true;
        }




        public ArrayList<int[]> neighbors(int row, int col) {
            ArrayList<int[]> map = new ArrayList<>();
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (!(i == 0 && j == 0)) {
                        int rowVal = row + (i);
                        int colVal = col + (j);
                        if (rowVal >= 0 && rowVal < boggleArray.length && colVal >=0
                                && colVal < boggleArray[0].length) {
                            map.add(new int[]{row + i, col + j});
                        }
                    }
                }
            }
            return map;
        }


    public ArrayList<String> solve() {
        for (int i = 0; i < boggleArray.length; i++) {
            for (int j = 0; j < boggleArray[0].length; j++) {
                searchWord(new Node(boggleArray[i][j]), i, j);
            }
        }
        ArrayList<String> newArr = new ArrayList<>();
        while(!answerWords.isEmpty()) {
            String bigger = "";
            for(String animal : answerWords) {
                if(animal.length() > bigger.length()) {
                    bigger = animal;
                }
            }
            newArr.add(bigger);
            while(answerWords.contains(bigger)) {
                answerWords.remove(bigger);
            }
        }

        ArrayList<String> otherArr = new ArrayList<>();
        for (int i = 0; i < this.words; i++) {
            otherArr.add(newArr.get(i));
        }
        otherArr.sort(Comparator.comparing(String::length).reversed().thenComparing(String::compareTo));
        ArrayList<String> dups = new ArrayList<>();
        for (int i = 0; i < otherArr.size(); i++) {
            if (!dups.contains(otherArr.get(i))) {
                dups.add(otherArr.get(i));
            }
        }
        /*
        while (!newArr.isEmpty()) {
            String one = newArr.get(0);
            String two = newArr.get(1);
            if (one.length() == two.length() && !one.equals(two)) {
                int count = 0;
                while (one.charAt(count) == two.charAt(count)) {
                    count++;
                }
                if (one.charAt(count) < two.charAt(count)) {
                    otherArr.add(one);
                    newArr.remove(one);
                }
            }
            else {
                if (one.equals(two)) {
                    otherArr.add(one);
                    newArr.remove(one);
                    newArr.remove(two);
                } else {
                    otherArr.add(one);
                    otherArr.add(two);
                    newArr.remove(one);
                    newArr.remove(two);
                }

            }
        }*/
        int val;
        val = Math.min(otherArr.size(), this.words);
        ArrayList<String> returnArr = new ArrayList<>();
        for (int p = 0; p < val; p ++) {
            returnArr.add(otherArr.get(p));
        }
        return returnArr;

    }

    public boolean isWord(String word) {
        if (dictionary.contains(word)) {
            return true;
        }
        return false;
    }

    public boolean existsPrefix(String word) {
        for (int z = 0; z < dictionary.size(); z++) {
            int len = word.length();
            if (word.equals(dictionary.get(z).substring(0,len))) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int words = 0;
        String dictFile;
        List<String> dict = new ArrayList<String>();
        HashMap<String, String> newMap = new HashMap<>();
        for (int m = 0; m < args.length; m = m + 2) {
            newMap.put(args[m], args[m + 1]);
        }
        String[] mapStrings = newMap.keySet().toArray(new String[0]);
        for (int x = 0; x < newMap.size(); x++) {
            String val = mapStrings[x];
            if (val.equals("-k")) {
                words = Integer.parseInt(newMap.get(val));
            } else if (val.equals("-d")) {
                ArrayList<Character> charArr = new ArrayList<>();
                String line = newMap.get(val);
                dictFile = line;
                try {
                    dict = Files.readAllLines(Paths.get(dictFile));
                }
                catch (IOException EX) {
                    EX.printStackTrace();
                }


            }

        }
        if (!(newMap.get("<") == null)) {
            try {
                Scanner first = new Scanner(new File(newMap.get("<")));
                String f = first.nextLine();
                ArrayList<String> newArr = new ArrayList<>();
                newArr.add(f);
                while (first.hasNextLine()) {
                    String s = first.nextLine();
                    newArr.add(s);
                }
                char[][] board = new char[newArr.size()][f.length()];
                for (int i = 0; i < newArr.size(); i++) {
                    char[] lol = newArr.get(i).toCharArray();
                    for (int j = 0; j < f.length(); j++) {
                        board[i][j] = lol[j];
                    }
                }
                Boggle newBoggle = new Boggle(words, dict, board);
                newBoggle.solve();
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }



    }

}
