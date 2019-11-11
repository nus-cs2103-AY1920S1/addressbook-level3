package seedu.address.logic.autocomplete.nodes;

import java.util.Collections;
import java.util.SortedSet;

/**
 * Represents an {@code AutoCompleteNode} that returns no values.
 */
public class EmptyAutoCompleteNode extends AutoCompleteNode<String> {

    private static EmptyAutoCompleteNode theOne;

    private EmptyAutoCompleteNode(String pointer) {
        super(pointer);
    }

    public static EmptyAutoCompleteNode getInstance() {
        if (theOne == null) {
            theOne = new EmptyAutoCompleteNode("");
        }
        return theOne;
    }

    @Override
    public SortedSet<String> getValues() {
        return Collections.emptySortedSet();
    }

}
