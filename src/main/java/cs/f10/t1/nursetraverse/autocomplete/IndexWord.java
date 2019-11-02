package cs.f10.t1.nursetraverse.autocomplete;

/**
 * Represents an index word(eg: 1, 2, 3) in application
 */
public class IndexWord extends AutoCompleteWord {

    public IndexWord(String suggestedIndex, String description) {
        super(suggestedIndex, description);
    }

    @Override
    public String getConnectorChar() {
        return " ";
    }
}
