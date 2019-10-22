package seedu.address.logic.search;

import java.util.Comparator;

/**
 * Utility class to find the first and the last index of key using Binary Search.
 */
public class BinarySearch {

    /**
     * Searches the specified array for the specified value using modification of binary
     * search algorithm and returns the index of the first key in list[] that equals the search key,
     * or -1 if no such key were found.
     *
     * @param list       - the array of keys to be searched
     * @param key        - the value to be searched for
     * @param comparator - the comparator by which array is ordered
     * @return - the index of the last key in list that equals the search key, -1 if not found
     * @throws NullPointerException - if list is null
     * @throws NullPointerException - if key is null
     * @throws NullPointerException - if comparator is null
     */
    public static <T> int firstIndexOf(T[] list, T key, Comparator<T> comparator) {
        if (list == null || key == null || comparator == null) {
            throw new IllegalArgumentException();
        }

        if (list.length == 0) {
            return -1;
        }
        if (comparator.compare(list[0], key) == 0) {
            return 0;
        }

        int lo = 0;
        int hi = list.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = comparator.compare(list[mid], key);

            if (cmp >= 1) {
                hi = mid - 1;
            } else if (cmp <= -1) {
                lo = mid + 1;
            } else if (comparator.compare(list[mid - 1], list[mid]) == 0) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * Searches the specified array for the specified value using modification of binary
     * search algorithm and returns the index of the last key in list[] that equals the search key,
     * or -1 if no such key were found.
     *
     * @param list       - the array of keys to be searched
     * @param key        - the value to be searched for
     * @param comparator - the comparator by which array is ordered
     * @return - the index of the last key in list that equals the search key, -1 if not found
     * @throws NullPointerException - if list is null
     * @throws NullPointerException - if key is null
     * @throws NullPointerException - if comparator is null
     */
    public static <T> int lastIndexOf(T[] list, T key, Comparator<T> comparator) {
        if (list == null || key == null || comparator == null) {
            throw new IllegalArgumentException();
        }

        if (list.length == 0) {
            return -1;
        }
        if (comparator.compare(list[list.length - 1], key) == 0) {
            return list.length - 1;
        }

        int lo = 0;
        int hi = list.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = comparator.compare(list[mid], key);

            if (cmp >= 1) {
                hi = mid - 1;
            } else if (cmp <= -1) {
                lo = mid + 1;
            } else if (comparator.compare(list[mid + 1], list[mid]) == 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
