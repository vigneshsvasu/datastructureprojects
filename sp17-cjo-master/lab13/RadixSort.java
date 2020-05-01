/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra
 * @version 1.4 - April 14, 2016
 *
 **/

public class RadixSort
{

    /**
     * Does Radix sort on the passed in array with the following restrictions:
     *  The array can only have ASCII Strings (sequence of 1 byte characters)
     *  The sorting is stable and non-destructive
     *  The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     **/
    public static String[] sort(String[] asciis)
    {
        String[] fullAsciis = splitArr(asciis);
        fullAsciis = sortHelper(fullAsciis);
        fullAsciis = clean(fullAsciis);
        return fullAsciis;
    }

    static String[] clean(String[] arr) {
        String[] newAsciis = new String[arr.length];
        for (int j = 0; j < arr.length; j++) {
            if (arr[j].charAt(0) == '!') {
                int count = 0;
                while (arr[j].charAt(count) == '!') {
                    count++;
                }
                newAsciis[j] = arr[j].substring(count, arr[j].length());
            }
            else {
                newAsciis[j] = arr[j];
            }
        }
        return newAsciis;
    }

    /**
     * Radix sort helper function that recursively calls itself to achieve the sorted array
     *  destructive method that changes the passed in array, asciis
     *
     * @param asciis String[] to be sorted
     *
     **/
    private static String[] sortHelper(String[] asciis)
    {
        int n = asciis.length;
        String[] fixed = new String[n];

        for (int d = 256-1; d >= 0; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[258];
            for (int i = 0; i < n; i++){
                count[asciis[i].charAt(d) + 1]++;
            }

            // compute cumulates
            for (int r = 0; r < 257; r++) {
                count[r+1] += count[r];
            }

            // move data
            for (int i = 0; i < n; i++) {
                fixed[count[asciis[i].charAt(d)]++] = asciis[i];
            }

            for (int i = 0; i < n; i++){
                asciis[i] = fixed[i];
            }

        }

        return asciis;
    }

    private static String[] splitArr(String[] asciis) {
        String[] newAsciis = new String[asciis.length];
        for (int j = 0; j < asciis.length; j++) {
            if (asciis[j].length() < 256) {
                String newStr = "";
                for (int i = 0; i < 256 - asciis[j].length(); i++) {
                    newStr = newStr + "!";
                }
                newAsciis[j] = newStr + asciis[j];
            }
            else {
                 newAsciis[j] = asciis[j];
            }
        }
        return newAsciis;
    }



}
