import java.util.Arrays;
import java.util.Comparator;

public class RangeBinarySearch {
    // Returns the index of the first key in a[] that equals the search key, or -1 if no such key.
    // Complexity: O(log N), where N is the length of the array

    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator comparator) {
        int first = 0;
        int last = a.length - 1;
        int middle = (first + last) / 2;
        int currentIndexOfKey = -1;                                               // if the key is not found in the array a, then -1 returns

        if(a == null || key == null || comparator == null){
            throw new java.lang.NullPointerException();
        }

        while(first < last){
            if(comparator.compare(key, a[middle]) == 0){                          //key and a[middle] is the same
                currentIndexOfKey = middle;
                last = middle - 1;
            } else if(comparator.compare(key, a[middle]) < 0){                   //key are behind
                last = middle - 1;
            } else {                                                             //key are behind
                first = middle + 1;
            }
            middle = (first + last)/2;
        }
        return currentIndexOfKey;
    }

    // Returns the index of the last key in a[] that equals the search key, or -1 if no such key.
    // Complexity: O(log N)
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator comparator){
        int first = 0;
        int last = a.length - 1;
        int middle = (first + last) / 2;
        int currentIndexOfKey = -1;                                                  // if the key is not found in the array a, then -1 returns

        if(a == null || key == null || comparator == null){
            throw new java.lang.NullPointerException();
        }
        
        while(first < last){
            if(comparator.compare(key, a[middle])== 0){                             //key and a[middle] is the same
                currentIndexOfKey = middle;
                first = middle + 1;
            } else if(comparator.compare(key, a[middle]) < 0){                        //key are behind
                last = middle - 1;
            } else {                                                                 //key are behind
                first = middle + 1;
            }
            middle = (first + last)/2;
        }

        return currentIndexOfKey;
    }
}