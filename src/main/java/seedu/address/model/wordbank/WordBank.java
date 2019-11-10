// @@author chrischenhui
package seedu.address.model.wordbank;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.card.Card;
import seedu.address.model.card.Meaning;

/**
 * Word bank stores multiple word - meaning pair
 * Duplicates of cards are not allowed (by Card#isSameName(Card) comparison)
 */
public class WordBank implements ReadOnlyWordBank {
    private final UniqueCardList cards;
    private String name;

    /**
     * Creates a word bank with the unique name.
     *
     * @param name of the word bank.
     */
    public WordBank(String name) {
        this.name = name;
        cards = new UniqueCardList();
    }

    /**
     * Resets the existing data of this {@code WordBank} with {@code newData}.
     */
    public void resetData(ReadOnlyWordBank newData) {
        requireNonNull(newData);
        setCards(newData.getCardList());
    }

    /**
     * Replaces the contents of the card list with {@code cards}.
     * {@code cards} must not contain any cards with the same meaning.
     */
    public void setCards(List<Card> cards) {
        requireNonNull(cards);
        this.cards.setCards(cards);
    }

    /**
     * Returns true if a card with the same meaning as {@code card} exists in the word bank.
     */
    @Override
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return cards.contains(card);
    }

    /**
     * Adds a card to the word bank.
     * A card with the same meaning must not already exist in the word bank.
     * The checking is handled by UniqueCardList class.
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Replaces the given card {@code target} in the list with {@code editedCard}.
     * {@code target} must exist in the word bank.
     * The card meaning of {@code editedCard} must not be the same as another existing card in the word bank.
     */
    public void setCard(Card target, Card editedCard) {
        requireNonNull(editedCard);
        cards.setCard(target, editedCard);
    }

    /**
     * Removes {@code key} from this {@code WordBank}.
     * {@code key} must exist in the word bank.
     */
    public void removeCard(Card card) {
        cards.remove(card);
    }

    /**
     * Returns the number of cards in the word bank.
     *
     * @return number of cards in the word bank.
     */
    @Override
    public int size() {
        return cards.size();
    }

    /**
     * Returns an unmodifiable view of the word bank.
     * This list will not contain any duplicate cards.
     * This is used so that any updates to the card list will be notified to the observer.
     */
    @Override
    public ObservableList<Card> getCardList() {
        return cards.asUnmodifiableObservableList();
    }

    /**
     * Returns a clone of the {@code card} at the specified {@index}.
     */
    @Override
    public Card getCard(Index index) {
        return cards.get(index).clone();
    }

    /**
     * Returns the card by searching for it's meaning.
     */
    @Override
    public Card getCard(Meaning meaning) {
        return cards.getCard(meaning);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WordBank // instanceof handles nulls
                && cards.equals(((WordBank) other).cards))
                && name.equals(((WordBank) other).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns true if both word banks have the same name.
     *
     * @param other word bank.
     * @return true if both word banks have the same name.
     */
    public boolean isSameName(WordBank other) {
        if (other == null) {
            return false;
        }
        String s1 = getName().toLowerCase();
        String s2 = other.getName().toLowerCase();
        return s1.equals(s2);
    }

    /**
     * Returns true if both word banks have the same name.
     *
     * @param other word bank.
     * @return true if both word banks have the same name.
     */
    public boolean isSameName(String other) {
        if (other == null) {
            return false;
        }
        String s1 = getName().toLowerCase();
        String s2 = other.toLowerCase();
        return s1.equals(s2);
    }

    /**
     * Returns the word bank's unique name.
     *
     * @return the word bank's unique name.
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
