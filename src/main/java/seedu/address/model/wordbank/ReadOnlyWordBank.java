package seedu.address.model.wordbank;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
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

    Card getCard(Index index);

    int size();

}
