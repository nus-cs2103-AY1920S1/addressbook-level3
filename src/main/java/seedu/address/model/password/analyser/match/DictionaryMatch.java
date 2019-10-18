package seedu.address.model.password.analyser.match;

/**
 * Represents a dictionary match found by an dictionary analyser.
 */
public class DictionaryMatch extends BaseMatch implements Comparable<DictionaryMatch> {
    private int rank;

    public DictionaryMatch(int startIndex, int endIndex, String token, int rank) {
        super(startIndex, endIndex, token);
        this.rank = rank;
    }

    @Override
    public int compareTo(DictionaryMatch o) {
        return this.rank - o.rank;
    }

    @Override
    public String toString() {
        return super.toString() + "Type : Dictionary Match\n" + "Rank : " + this.rank + "\n";
    }
}
