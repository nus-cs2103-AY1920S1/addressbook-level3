package seedu.address.model.autocomplete;

import java.util.Comparator;

/**
 * Word is an immutable data type that represents an autocomplete object.
 * A query string and an associated integer weight(optional)
 * Represents single search word with the query and the weight.
 */
public class Word implements Comparable<Word> {

    private final String query;
    private final long weight;

    public Word() {
        this.query = null;
        this.weight = -1;
    }

    /**
     * Initializes a word with the specified query and the weight.
     * Word should be non-null and weight must be non-negative.
     *
     * @param query  - the query to be searched for
     * @param weight - the corresponding weight of the query
     * @throws NullPointerException     - if query == null
     * @throws IllegalArgumentException - if weight < 0
     */
    public Word(String query, long weight) {
        if (query == null) {
            throw new NullPointerException("Word can't be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be non-negative");
        }

        this.query = query;
        this.weight = weight;
    }

    /**
     * Returns comparator to compare words in lexicographic order using
     * only the first r characters of each query. Parameter r should be non-negative.
     *
     * @return -1 if first r characters of this are less than the first r characters of that
     * 0 if first r characters of this are equal to the first r characters of that
     * 1 if first r characters of this are larger than to the first r characters of that
     */
    public static Comparator<Word> compareCharSeq(int len) {
        if (len < 0) {
            throw new IllegalArgumentException("length must be non-negative, but was " + len);
        }
        return (t1, t2) -> {
            String q1 = truncateTarget(t1.query, len);
            String q2 = truncateTarget(t2.query, len);
            return Integer.compare(q1.compareToIgnoreCase(q2), 0);
        };
    }

    /**
     * For comparing first R chars
     *
     * @param targetString Desc string to compare
     * @param len
     * @return
     */
    private static String truncateTarget(String targetString, int len) {
        final int endIndex = Math.min(targetString.length(), len);
        return targetString.substring(0, endIndex);
    }

    /**
     * Returns comparator for comparing words using their corresponding weights.
     */
    public static Comparator<Word> compareWeightDescending() {
        return Comparator.comparingLong(Word::getWeight).reversed();
    }

    public long getWeight() {
        return weight;
    }

    public String getQuery() {
        return query;
    }

    /**
     * Returns a string representation of this word in the following format:
     * the weight, followed by a tab, followed by the query.
     */
    @Override
    public String toString() {
        return weight + "\t" + query;
    }

    /**
     * Compares two words in lexicographic order.
     *
     * @return -1 if this is (less than) that
     * 0 if this (is the same as) that
     * 1 if this (is larger than) that
     */
    @Override
    public int compareTo(Word word) {
        int cmp = this.query.toLowerCase().compareTo(word.query.toLowerCase());
        if (cmp <= -1) {
            return -1;
        } else if (cmp >= 1) {
            return 1;
        } else {
            return 0;
        }
    }
}
