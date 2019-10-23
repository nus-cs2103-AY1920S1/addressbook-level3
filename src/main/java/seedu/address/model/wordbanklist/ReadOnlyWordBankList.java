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
     */
    ObservableList<WordBank> getWordBankList();

    WordBank getWordBank(String name);

    int size();

    boolean hasWordBank(WordBank wordBank);
}
