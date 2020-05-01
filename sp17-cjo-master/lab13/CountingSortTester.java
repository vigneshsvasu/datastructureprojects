import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class CountingSortTester {

    /**
     * Array that will be properly sorted with CountingSort.naiveCountingSort
     **/

	@Test
	public void radixTest() {
		String[] uns = new String[]{"?????", "??", "???-", "???", "????A", "1", "?", "3?4", "?@", "??", "?b", "?cp???", "?s??k", "???", "q?", ">M", "???O]", "?A?", "???+?", "P?", "? ?", "??I?", "^???"};

		String[] s = RadixSort.sort(uns);
		Arrays.sort(uns);
		LinkedHashSet<String> l = new LinkedHashSet<>();
		for (int i = 0; i < uns.length; i++) {
			if (!uns[i].equals(s[i])) {
				System.out.println(uns[i]);
				System.out.println(s[i]);
				l.add(s[i]);
				l.add(uns[i]);
				System.out.println();
			}
		}
		System.out.println(l);
		assertArrayEquals(uns, s);
	}


    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests("all", CountingSortTester.class);
    }	
} 
