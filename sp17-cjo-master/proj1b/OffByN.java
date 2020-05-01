/**
 * Created by vigneshvasu on 2/8/17.
 */
public class OffByN implements CharacterComparator {
    private int nAmount;
    public OffByN(int N) {
        nAmount = N;
    }
    public boolean equalChars(char x, char y) {
        int diffval = x - y;
        diffval = Math.abs(diffval);
        if (diffval == nAmount) {
            return true;
        }
        return false;
    }
}
