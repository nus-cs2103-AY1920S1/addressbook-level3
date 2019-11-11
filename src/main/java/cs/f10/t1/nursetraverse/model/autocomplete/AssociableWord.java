package cs.f10.t1.nursetraverse.model.autocomplete;

import java.util.LinkedList;

/**
 * Interface class for words that has associated words
 */
public interface AssociableWord {
    LinkedList<String> getAssociatedWordList();
}
