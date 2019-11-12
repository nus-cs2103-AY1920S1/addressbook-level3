package seedu.address.model.password.analyser.match;

/**
 * Represents a {@code match} which was found  by {@code SequenceAnalyser}.
 */
public class SequenceMatch extends BaseMatch {

    /**
     * Constructs a {@code SequenceMatch}
     *
     * @param startIndex the start index in the {@code PasswordValue} which the sequence was found.
     * @param endIndex the end index in the {@code PasswordValue} which the sequence was found.
     * @param token the string in the {@code PasswordValue} which the sequence was found.
     */
    public SequenceMatch(int startIndex, int endIndex, String token) {
        super(startIndex, endIndex, token);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString() + "Type : Sequence Match\n";
    }
}
