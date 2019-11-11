// @@author chrischenhui
package seedu.address.model.wordbanklist;

import javafx.collections.ObservableList;
import seedu.address.model.wordbank.WordBank;

/**
 * Unmodifiable view of a word bank list.
 */
public interface ReadOnlyWordBankList {

    /**
     * Returns an unmodifiable view of the word bank.
     * This list will not contain any duplicate word banks.
     * Every word bank can be specified by its unique name.
     * This is used so that any updates to the word bank list will be notified to the observer.
     */
    ObservableList<WordBank> getFilteredWordBankList();

    /**
     * Retrieves a word bank from its unique name.
     *
     * @param name word bank name.
     * @return word bank.
     */
    WordBank getWordBankFromName(String name);

    /**
     * Checks if the word bank list contain this particular word bank.
     *
     * @param name of word bank.
     * @return true if word bank exist and false otherwise.
     */
    boolean hasWordBankName(String name);

    /**
     * Returns number of word banks in the word bank list.
     *
     * @return number of word banks in the word bank list.
     */
    int size();
}
