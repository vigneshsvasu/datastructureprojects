/**
 * Created by vigneshvasu on 2/8/17.
 */
public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int diffval = x - y;
        diffval = Math.abs(diffval);
        if (diffval == 1) {
            return true;
        }
        return false;
    }
}
