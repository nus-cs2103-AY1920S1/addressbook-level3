package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.card.UniqueCardList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameCard comparison)
 */
public class CardBook {

    private final UniqueCardList cards;

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

    public CardBook() {}

    /**
     * Creates an CardBook using the Persons in the {@code toBeCopied}
     */
    public CardBook(CardBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the card list with {@code cards}.
     * {@code cards} must not contain duplicate cards.
     */
    public void setCards(List<Card> cards) {
        this.cards.setCards(cards);
    }

    /**
     * Resets the existing data of this {@code CardBook} with {@code newData}.
     */
    public void resetData(CardBook newData) {
        requireNonNull(newData);

        setCards(newData.getCardList());
    }

    //// card-level operations

    /**
     * Returns true if a card with the same identity as {@code card} exists in the address book.
     */
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return cards.contains(card);
    }

    /**
     * Adds a card to the address book.
     * The card must not already exist in the address book.
     */
    public void addCard(Card c) {
        cards.add(c);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeCard(Card key) {
        cards.remove(key);
    }

    //// util methods

    public ObservableList<Card> getCardList() {
        return cards.asUnmodifiableObservableList();
    }

    @Override
    public String toString() {
        return cards.asUnmodifiableObservableList().size() + " cards";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CardBook // instanceof handles nulls
                && cards.equals(((CardBook) other).cards));
    }

    @Override
    public int hashCode() {
        return cards.hashCode();
    }
}
