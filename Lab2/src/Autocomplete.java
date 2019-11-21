
import java.util.*;

public class Autocomplete {
    // Initializes the data structure from the given array of terms.
    // Complexity: O(N log N), where N is the number of terms
    Term[] terms;
    Comparator<Term> c;

    public Autocomplete(Term[] terms) {
        this.terms = terms;
        c = Term.byLexicographicOrder();

        Arrays.sort(terms, c);

    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    // Complexity: O(log N + M log M), where M is the number of matching terms
    public Term[] allMatches(String prefix) {
        if (prefix == null){
            throw new NullPointerException();
        }
        Term[] ret = new Term[numberOfMatches(prefix)];
        Term t = new Term(prefix, 1);
        int pos = 0;

        if (ret.length > 0) {
            for (int i = RangeBinarySearch.firstIndexOf(terms, t, c); i <= RangeBinarySearch.lastIndexOf(terms, t, c); i++) {
                ret[pos] = terms[i];
                pos++;
            }
        }
        return ret;
    }

    // Returns the number of terms that start with the given prefix.
    // Complexity: O(log N)
    public int numberOfMatches(String prefix) {
        if (prefix == null){
            throw new NullPointerException();
        }
        c = Term.byPrefixOrder(prefix.length());
        int ret = 0;
        Term t = new Term(prefix, 1);

        if (RangeBinarySearch.firstIndexOf(terms, t, c) > -1) {
            for (int i = RangeBinarySearch.firstIndexOf(terms, t, c); i <= RangeBinarySearch.lastIndexOf(terms, t, c); i++) {
                ret++;
            }
        }
        return ret;
    }
}
