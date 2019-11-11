package seedu.address.model.password.analyser.match;

import java.util.Objects;

/**
 * Represents a {@code match} which was found in (@code Dictionary} used by {@code DictionaryAnalyser}.
 */
public class DictionaryMatch extends BaseMatch implements Comparable<DictionaryMatch> {
    private int rank;

    /**
     * Constructs a {@code DictionaryMatch}.
     *
     * @param startIndex the start index in the {@code PasswordValue} which the match was found.
     * @param endIndex the end index in the {@code PasswordValue} which the match was found.
     * @param token the string in the {@code PasswordValue} which the match was found.
     * @param rank the rank of the {code Match} in the dictionary.
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DictionaryMatch that = (DictionaryMatch) o;
        return rank == that.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank);
    }
}
