// @@author chrischenhui
package seedu.address.model.wordbank;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.card.Card;
import seedu.address.model.card.Meaning;
import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;

/**
 * A list of cards that enforces uniqueness between its elements and does not allow nulls.
 * A card is considered unique by comparing using {@code Card#isSameMeaning(Card)}. As such, adding and updating of
 * cards uses Card#isSameMeaning(Card) for equality so as to ensure that the person being added or updated is
 * unique in terms of names in UniqueCardList. However, the removal of a card uses Card#equals(Object) so
 * as to ensure that the card with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see seedu.address.model.card.Card#isSameMeaning(seedu.address.model.card.Card)
 */
public class UniqueCardList implements Iterable<Card> {

    private final ObservableList<Card> internalList = FXCollections.observableArrayList();
    private final ObservableList<Card> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains a card with the same meaning.
     */
    public boolean contains(Card toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMeaning);
    }

    /**
     * Adds a card to the list.
     * The card must not exist in the list.
     *
     * @see UniqueCardList#contains(Card)
     */
    public void add(Card toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCardException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the card {@code target} in the list with {@code editedCard}.
     * {@code target} must exist in the list.
     * The name of {@code editedPerson} must not be the same as another existing card in the list.
     */
    public void setCard(Card target, Card editedPerson) {
        requireAllNonNull(target, editedPerson);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CardNotFoundException();
        }
        if (!target.isSameMeaning(editedPerson) && contains(editedPerson)) {
            throw new DuplicateCardException();
        }
        internalList.set(index, editedPerson);
    }

    /**
     * Retrieves the card by searching for it using it's unique meaning.
     *
     * @param meaning of the card.
     * @return the card with that specific meaning.
     */
    public Card getCard(Meaning meaning) {
        requireAllNonNull(meaning);
        for (Card c : internalList) {
            if (c.getMeaning().equals(meaning)) {
                return c;
            }
        }
        return Card.createDummyCard();
    }

    /**
     * Removes the equivalent card from the list.
     * The card must exist in the list.
     */
    public void remove(Card toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CardNotFoundException();
        }
    }

    /**
     * Replaces the list of cards with a a new list of cards.
     *
     * @param replacement list of card.
     */
    public void setCards(UniqueCardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code cards}.
     * {@code cards} must not contain duplicate persons.
     */
    public void setCards(List<Card> cards) {
        requireAllNonNull(cards);
        if (!cardsAreUnique(cards)) {
            throw new DuplicateCardException();
        }

        internalList.setAll(cards);
    }

    /**
     * Retrieves a card using it's index.
     *
     * @param index of the card.
     * @return the card with that index.
     */
    public Card get(Index index) {
        return internalList.get(index.getZeroBased());
    }

    /**
     * Returns the size of this list.
     *
     * @return size of this list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Card> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Card> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCardList // instanceof handles nulls
                && internalList.equals(((UniqueCardList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code cards} contains only unique cards.
     */
    private boolean cardsAreUnique(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                if (cards.get(i).isSameMeaning(cards.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
