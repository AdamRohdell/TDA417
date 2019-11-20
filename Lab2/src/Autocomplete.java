import java.util.Comparator;

public class Autocomplete {
    // Initializes the data structure from the given array of terms.
    // Complexity: O(N log N), where N is the number of terms
    Term[] terms;
    Comparator<Term> c;
    public Autocomplete(Term[] terms){
        this.terms = terms;
        c = Term.byLexicographicOrder();

        quickSort(this.terms, 0, this.terms.length-1);
        
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    // Complexity: O(log N + M log M), where M is the number of matching terms
    public Term[] allMatches(String prefix){
    }

    // Returns the number of terms that start with the given prefix.
    // Complexity: O(log N)
    public int numberOfMatches(String prefix){
        c = Term.byPrefixOrder(prefix.length());
        int ret = 0;
        Term t = new Term(prefix, 1);
        if (RangeBinarySearch.firstIndexOf(terms, t, c)> -1){
            ret++;
        }

        return ret;
    }


    private quickSort(Term[] terms, int low, int high){
 
        int partition = partition(terms, low, high);
 
        if(partition-1>low) {
            quickSort(terms, low, partition - 1);
        }
        if(partition+1<high) {
            quickSort(arr, partition + 1, high);
        }
    }
    
    private int partition(Term[] terms, int low, int high){
        Term pivot = terms[median3(low, (low+high)/2, high)];

        for(int i=low; i<high; i++){
            if(c.compare(terms[i],pivot) < 0){
                Term temp= terms[low];
                terms[low]=terms[i];
                terms[i]=temp;
                low++;
            }
        }

        Term temp = terms[low];
        terms[low] = pivot;
        terms[high] = temp;
 
        return low;
    }

    private static final int median3(int i, int j, int k) {
        if (i <= j) {
            if (j <= k) return j;      /* a[i] <= a[j] <= a[k] */
            else if (i <= k) return k; /* a[i] <= a[k] <  a[j] */
            else return i;                   /* a[k] <  a[i] <= a[j] */
        } else {
            if (k <= j) return j;      /* a[k] <= a[j] <  a[i] */
            else if (k <= i) return k; /* a[j] <  a[k] <= a[i] */
            else return i;                   /* a[j] <  a[i] <  a[k] */
        }
    }
 
}
