package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.UniqueDeadlineList;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.UniqueFlashCardList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameFlashCard comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueFlashCardList flashCards;
    private final UniqueDeadlineList deadlines;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        flashCards = new UniqueFlashCardList();
        deadlines = new UniqueDeadlineList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Flashcards in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the flashCard list with {@code flashCards}.
     * {@code flashCards} must not contain duplicate flashCards.
     */
    public void setFlashCards(List<FlashCard> flashCards) {
        this.flashCards.setFlashCards(flashCards);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setFlashCards(newData.getFlashcardList());
    }

    //// flashCard-level operations

    /**
     * Returns true if a flashCard with the same identity as {@code flashCard} exists in the address book.
     */
    public boolean hasFlashcard(FlashCard flashCard) {
        requireNonNull(flashCard);
        return flashCards.contains(flashCard);
    }

    /**
     * Adds a flashCard to the address book.
     * The flashCard must not already exist in the address book.
     */
    public void addFlashcard (FlashCard c) {
        flashCards.add(c);
    }

    /**
     * Replaces the given flashCard {@code target} in the list with {@code editedFlashCard}.
     * {@code target} must exist in the address book.
     * The flashCard identity of {@code editedFlashCard} must not be the same as another existing
     * flashCard in the address book.
     */
    public void setFlashcard(FlashCard target, FlashCard editedFlashCard) {
        requireNonNull(editedFlashCard);

        flashCards.setFlashcard(target, editedFlashCard);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeFlashCard(FlashCard key) {
        flashCards.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return flashCards.asUnmodifiableObservableList().size() + "flash cards";
        // TODO: refine later
    }

    @Override
    public ObservableList<FlashCard> getFlashcardList() {
        return flashCards.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Deadline> getDeadlineList() {
        return deadlines.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && flashCards.equals(((AddressBook) other).flashCards));
    }

    @Override
    public int hashCode() {
        return flashCards.hashCode();
    }

    // Deadline methods

    public void addDeadline(Deadline d) {
        deadlines.add(d);
    }

    public void setDeadlines(List<Deadline> deadlines) {
        this.deadlines.setDeadline(deadlines);
    }

    public void removeDeadline(Deadline key) {
        deadlines.remove(key);
    }

}
