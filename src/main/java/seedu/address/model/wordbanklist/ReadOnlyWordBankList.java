package seedu.address.model.wordbanklist;

import javafx.collections.ObservableList;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbank.exceptions.WordBankNotFoundException;

/**
 * Unmodifiable view of a word bank list.
 */
public interface ReadOnlyWordBankList {

    /**
     * Returns an unmodifiable view of the word bank.
     * This list will not contain any duplicate word banks.
     * Every word bank can be specified by its unique name.
     */
    ObservableList<WordBank> getFilteredWordBankList();

    /**
     * Retrieves a word bank from its unique name.
     *
     * @param name word bank name.
     * @return word bank.
     * @throws WordBankNotFoundException
     */
    WordBank getWordBankFromName(String name) throws WordBankNotFoundException;

    boolean hasWordBankName(String name);

    int size();
}
