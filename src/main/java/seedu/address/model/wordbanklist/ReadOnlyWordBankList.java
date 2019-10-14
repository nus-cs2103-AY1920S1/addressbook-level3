package seedu.address.model.wordbanklist;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.wordbank.WordBank;

/**
 * Unmodifiable view of a word bank.
 */
public interface ReadOnlyWordBankList {

    /**
     * Returns an unmodifiable view of the word bank.
     * This list will not contain any duplicate cards.
     */
    ObservableList<WordBank> getWordBankList();

    WordBank getWordBank(Index index);

    int size();

}
