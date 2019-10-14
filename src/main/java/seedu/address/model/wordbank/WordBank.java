package seedu.address.model.wordbank;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.card.Card;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by Card#isSameMeaning(Card) comparison)
 */
public class WordBank implements ReadOnlyWordBank {

    private final UniqueCardList cards;
    private String name;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        cards = new UniqueCardList();
    }

    public WordBank() {}

    /**
     * Creates a WordBank using the Cards in the {@code toBeCopied}
     */
    public WordBank(ReadOnlyWordBank toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the card list with {@code cards}.
     * {@code cards} must not contain any cards with the same meaning.
     */
    public void setCard(List<Card> cards) {
        this.cards.setCard(cards);
    }

    /**
     * Resets the existing data of this {@code WordBank} with {@code newData}.
     */
    public void resetData(ReadOnlyWordBank newData) {
        requireNonNull(newData);

        setCard(newData.getCardList());
    }

    //// card-level operations

    /**
     * Returns true if a card with the same meaning as {@code card} exists in the word bank.
     */
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return cards.contains(card);
    }

    /**
     * Adds a card to the word bank.
     * A card with the same meaning must not already exist in the word bank.
     */
    public void addCard(Card p) {
        cards.add(p);
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
    public void removeCard(Card key) {
        cards.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return cards.asUnmodifiableObservableList().size() + " cards";
        // TODO: refine later
    }

    @Override
    public int size() {
        return cards.size();
    }

    @Override
    public ObservableList<Card> getCardList() {
        return cards.asUnmodifiableObservableList();
    }

    @Override
    public Card getCard(Index index) {
        return cards.get(index);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WordBank // instanceof handles nulls
                && cards.equals(((WordBank) other).cards));
    }

    @Override
    public int hashCode() {
        return cards.hashCode();
    }
    /**
     * Returns true if both cards have the same meaning.
     */
    public boolean isSameMeaning(WordBank other) {
        if (other == null) {
            return false;
        }
        return getName().equals(other.getName());
    }

    public String getName() {
        return name;
    }

}
