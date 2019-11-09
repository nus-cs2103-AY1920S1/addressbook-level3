package seedu.address.logic.nodes;

import java.util.Collections;
import java.util.SortedSet;

import seedu.address.logic.AutoCompleteNode;

public class EmptyAutoCompleteNode extends AutoCompleteNode<String> {

    private static EmptyAutoCompleteNode theOne;

    public static EmptyAutoCompleteNode getInstance() {
        if (theOne == null) {
            theOne = new EmptyAutoCompleteNode("");
        }
        return theOne;
    }

    private EmptyAutoCompleteNode(String pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        return Collections.emptySortedSet();
    }

}
