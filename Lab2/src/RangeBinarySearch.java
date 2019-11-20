import java.util.Arrays;
import java.util.Comparator;

public class RangeBinarySearch {
    // Returns the index of the first key in a[] that equals the search key, or -1 if no such key.
    // Complexity: O(log N), where N is the length of the array
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator){
        if (a == null || key == null || comparator == null){
            throw new NullPointerException();
        }
        int low = 0;
        int high = a.length-1;
        int mid = (low+high)/2;
        int currentIndexOfKey = -1;
        while (low < high){
            if (comparator.compare(key, a[mid]) > 0){
                low = mid + 1;
            } else if (comparator.compare(key, a[mid]) < 0){
                high = mid - 1; 
            } else {
                currentIndexOfKey = mid;
                high = mid - 1;
            }
            mid  = (low + high)/2;
        }
        return currentIndexOfKey;
    }

    // Returns the index of the last key in a[] that equals the search key, or -1 if no such key.
    // Complexity: O(log N)
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator){
        if (a == null || key == null || comparator == null){
            throw new NullPointerException();
        }
        int low = 0;
        int high = a.length-1;
        int mid = (low+high)/2;
        int currentIndexOfKey = -1;
        while (low < high){
            if (comparator.compare(key, a[mid]) > 0){
                low = mid + 1;
            } else if (comparator.compare(key, a[mid]) < 0){
                high = mid - 1; 
            } else {
                currentIndexOfKey = mid;
                low = mid + 1;
            }
            mid  = (low + high)/2;
        }
        return currentIndexOfKey;
    }
}