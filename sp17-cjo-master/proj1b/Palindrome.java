/**
 * Created by vigneshvasu on 2/8/17.
 */
public class Palindrome {
    public static Deque<Character> wordToDeque(String word) {
        Deque<Character> newDeque = new ArrayDequeSolution<Character>();
        for (int k = 0; k < word.length(); k++) {
            char x = word.charAt(k);
            newDeque.addLast(x);
        }
        return newDeque;
    }


    public static boolean isPalindrome(String word) {
        int first = 0;
        int sec = word.length() - 1;
        while (sec > first) {
            if (word.charAt(first) != word.charAt(sec)) {
                return false;
            }
            first = first + 1;
            sec = sec - 1;
        }
        return true;
    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {
        int first = 0;
        int sec = word.length() - 1;
        while (sec > first) {
            if (!cc.equalChars(word.charAt(first), word.charAt(sec))) {
                return false;
            }
            first = first + 1;
            sec = sec - 1;
        }
        return true;
    }

    private static void main(String[] args) {
        String word = "word";
        Deque<Character> test;
        test = wordToDeque(word);
        System.out.println(test.toString());
        String otherWord = "poop";
        System.out.println(isPalindrome(otherWord));
        OffByOne ovoxo = new OffByOne();
        String dumbWord = "flaake";
        System.out.println(isPalindrome(dumbWord, ovoxo));
        OffByN damn = new OffByN(5);
        System.out.println(damn.equalChars('a', 'f'));
    }

}
