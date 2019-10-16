package seedu.address.model;

import java.util.Stack;

/**
 * Unmodifiable view of a model history.
 */
public interface ReadOnlyModelHistory {

    Stack<Model> getPastModels();

    Stack<Model> getFutureModels();

}
