package cs.f10.t1.nursetraverse.autocomplete;

/**
 * Represents an object word(eg: patient, med, medcon) in application
 */
public class ObjectWord extends AutoCompleteWord {
    public ObjectWord(String suggestedObject) {
        super(suggestedObject);
    }

    @Override
    public String getConnectorChar() {
        return "-";
    }
}
