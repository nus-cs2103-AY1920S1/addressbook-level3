package seedu.address.model.autocomplete;

import java.util.Arrays;

import seedu.address.logic.search.BinarySearch;

/**
 * Given the data and the query, this class is for searching for words in the data
 * starting with the given query and returns the array in descending order w.r.t weight.
 */
public class AutoCompleteModel {

    private final Word[] dataCopy;

    /**
     * Initialize an AutoCompleteModel using array of words.
     *
     * @param data - the array of queries
     * @throws NullPointerException - if queries == null
     */
    public AutoCompleteModel(Word[] data) {
        if (data == null) {
            throw new NullPointerException("Data cannot be null");
        }
        dataCopy = Arrays.copyOf(data, data.length);
        Arrays.sort(dataCopy);
    }

    /**
     * Return all words that start with the given query, in descending order of weight.
     * Query should be non-null.
     *
     * @param query - query to be searched for
     * @return - array of words that match the given query in descending order
     * @throws NullPointerException - if query == null
     */
    public Word[] allMatches(String query) {
        if (query == null) {
            throw new NullPointerException("Query cannot be null");
        }
        Word queryWord = new Word(query, 100);
        int firstIndex = BinarySearch.firstIndexOf(dataCopy, queryWord, Word.compareCharSeq(query.length()));

        // NOT FOUND
        if (firstIndex == -1) {
            return new Word[0];
        }
        int lastIndex = BinarySearch.lastIndexOf(dataCopy, queryWord, Word.compareCharSeq(query.length()));

        int range = lastIndex - firstIndex + 1;
        Word[] allMatches = new Word[range];
        int j = 0;
        for (int i = firstIndex; i <= lastIndex; i++) {
            allMatches[j++] = dataCopy[i];
        }
        Arrays.sort(allMatches, Word.compareWeightDescending());
        return allMatches;
    }
}
