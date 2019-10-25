package seedu.address.model.wordbanklist;

import javafx.collections.ObservableList;
import seedu.address.model.card.exceptions.WordBankNotFoundException;
import seedu.address.model.wordbank.WordBank;

import java.util.Optional;

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


    /**
     * Retrieves a word bank from its unique name
     *
     * @param name word bank name.
     * @return word bank.
     * @throws WordBankNotFoundException
     */
    WordBank getWordBankFromName(String name) throws WordBankNotFoundException;

    int size();
}
