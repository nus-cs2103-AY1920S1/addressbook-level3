// @@author chrischenhui
package seedu.address.model.wordbank;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.card.Card;
import seedu.address.model.card.Meaning;

/**
 * Unmodifiable view of a word bank.
 */
public interface ReadOnlyWordBank {
    /**
     * Returns an unmodifiable view of the word bank.
     * This list will not contain any duplicate cards.
     */
    ObservableList<Card> getCardList();

    /**
     * Returns a clone of the {@code card} at the specified {@index}.
     */
    Card getCard(Index index);

    /**
     * Returns the card by searching for it's meaning.
     */
    Card getCard(Meaning meaning);

    /**
     * Returns true if a card with the same meaning as {@code card} exists in the word bank.
     */
    boolean hasCard(Card c);

    /**
     * Returns the number of cards in the word bank.
     *
     * @return number of cards in the word bank.
     */
    int size();

    /**
     * Returns the word bank's unique name.
     *
     * @return the word bank's unique name.
     */
    String getName();
}
