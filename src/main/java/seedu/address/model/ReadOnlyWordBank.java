package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.card.Card;

/**
 * Unmodifiable view of a word bank.
 */
public interface ReadOnlyWordBank {

    /**
     * Returns an unmodifiable view of the word bank.
     * This list will not contain any duplicate cards.
     */
    ObservableList<Card> getCardList();

}
