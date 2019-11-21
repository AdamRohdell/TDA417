
import java.util.*;

public class Autocomplete {
    // Initializes the data structure from the given array of terms.
    // Complexity: O(N log N), where N is the number of terms
    Term[] terms;
    Comparator<Term> c;

    public Autocomplete(Term[] terms) {
        if(terms == null){
            throw new java.lang.NullPointerException();
        }

        for(Term t : terms){
            if (t == null){
                throw new java.lang.NullPointerException();
            }
        }

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

        Term t = new Term(prefix, 1);

        int first = RangeBinarySearch.firstIndexOf(terms, t, c);
        int last = RangeBinarySearch.lastIndexOf(terms, t, c);
        if(first < 0){
            return new Term[0];
        }

        Term[] ret = Arrays.copyOfRange(terms, first, last);

        c = Term.byReverseWeightOrder();
        Arrays.sort(ret, c);

        return ret;
        // O(log N + N + M log M)
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
