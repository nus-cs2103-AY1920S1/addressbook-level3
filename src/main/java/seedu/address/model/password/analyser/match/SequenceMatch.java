package seedu.address.model.password.analyser.match;

/**
 * Represents a sequence match found by a sequence analyser.
 */
public class SequenceMatch extends BaseMatch {

    public SequenceMatch(int startIndex, int endIndex, String token) {
        super(startIndex, endIndex, token);
    }

    @Override
    public String toString() {
        return super.toString() + "Type : Sequence Match\n";
    }
}
