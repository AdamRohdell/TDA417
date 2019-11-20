import java.util.Comparator;

public class Term {
    private String query;
    private Long weight;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight){
        if (query != null){
            this.query = query;
        } else {
            throw new NullPointerException();
        }
        if (weight > 0){
            this.weight = weight;
        } else {
            throw new IllegalArgumentException();
        }
        
    }

    private static class LexicographicComparator implements Comparator<Term>{
        public int compare(Term a, Term b){
            return a.query.compareTo(b.query);
        }
    }
    // Compares the two terms in lexicographic order by query.
    public static Comparator<Term> byLexicographicOrder(){
        return new LexicographicComparator();
    }

    private static class ReverseWeightComperator implements Comperator<Term>{
        public int compare(Term a, Term b){
            return b.weight.compareTo(a.weight);
        }
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder(){
        return new ReverseWeightComperator();
    }

    private static class ByPrefixOrderComperator implements Comperator<Term>{
        private int k;
        private ByPrefixOrderComperator(int k){
            this.k = k;
        }

        public int compare(Term a, Term b){
            a.query.substring(0,k).compareTo(b.substring(0,k));
        }
    }
    // Compares the two terms in lexicographic order,
    // but using only the first k characters of each query.
    public static Comparator<Term> byPrefixOrder(int k){
        if (k >= 0){
            return new ByPrefixOrderComperator(k);
        } 
        throw new IllegalArgumentException();
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by whitespace, followed by the query.
    public String toString() {
        return String.format("%12d    %s", this.weight, this.query);
    }



}
