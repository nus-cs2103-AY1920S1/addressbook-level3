package seedu.address.autocomplete;

import java.util.LinkedList;

/**
 * Interface class for words that has associated words
 */
public interface AssociableWord {
    LinkedList<String> getAssociatedWordList();
}
