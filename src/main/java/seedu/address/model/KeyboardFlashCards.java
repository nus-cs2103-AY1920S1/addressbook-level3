package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.category.Category;
import seedu.address.model.category.UniqueCategoryList;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.UniqueDeadlineList;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.UniqueFlashCardList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameFlashCard comparison)
 */
public class KeyboardFlashCards implements ReadOnlyKeyboardFlashCards {

    private final UniqueFlashCardList flashCards;
    private final UniqueDeadlineList deadlines;
    private final UniqueCategoryList categories;
    private final int[] stats;

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
        categories = new UniqueCategoryList();
        stats = new int[3]; //good,hard,easy
    }

    public KeyboardFlashCards() {}

    /**
     * Creates an KeyboardFlashCards using the Flashcards in the {@code toBeCopied}
     */
    public KeyboardFlashCards(ReadOnlyKeyboardFlashCards toBeCopied) {
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
        //only when setFlashCards is success the set the categories
        setCategories(flashCards);

    }

    public void setCategories(List<FlashCard> flashCards) {
        //clear the existing data
        categories.clear();
        flashCards.forEach(flashCard -> categories.add(flashCard.getCategories()));
    }


    /**
     * Resets the existing data of this {@code KeyboardFlashCards} with {@code newData}.
     */
    public void resetData(ReadOnlyKeyboardFlashCards newData) {
        requireNonNull(newData);

        setFlashCards(newData.getFlashcardList());
        setDeadlines(newData.getDeadlineList());
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
    public void addFlashcard(FlashCard c) {
        flashCards.add(c);
        //update the categoryList
        addCategory(c.getCategories());

    }

    /**
     * Replaces the given flashCard {@code target} in the list with {@code editedFlashCard}.
     * {@code target} must exist in the address book.
     * The flashCard identity of {@code editedFlashCard} must not be the same as another existing
     * flashCard in the address book.
     */
    public void addDeadline(Deadline d) {
        deadlines.add(d);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addCategory(Set<Category> categorySet) {
        categories.add(categorySet);
    }

    public void setFlashcard(FlashCard target, FlashCard editedFlashCard) {
        requireNonNull(editedFlashCard);
        flashCards.setFlashcard(target, editedFlashCard);
        categories.remove(target.getCategories());
        categories.add(editedFlashCard.getCategories());
    }


    /**
     * Removes {@code key} from this {@code KeyboardFlashCards}.
     * {@code key} must exist in the address book.
     */
    public void removeFlashCard(FlashCard key) {
        flashCards.remove(key);
        categories.remove(key.getCategories());
    }

    public void removeDeadline(Deadline key) {
        deadlines.remove(key);
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
    public ObservableList<Category> getCategoryList() {
        return categories.asUnmodifiableObservablelist();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof KeyboardFlashCards // instanceof handles nulls
                && flashCards.equals(((KeyboardFlashCards) other).flashCards));
    }

    @Override
    public int hashCode() {
        return flashCards.hashCode();
    }

    // Deadline methods

    /**
     * Replaces the given deadline {@code target} in the list with {@code editedDeadline}.
     * {@code target} must exist in the address book.
     * The deadline identity of {@code editedFlashCard} must not be the same as another existing
     * deadline in the address book.
     */
    public void setDeadline(Deadline target, Deadline editedDeadline) {
        requireNonNull(editedDeadline);
        deadlines.setDeadline(target, editedDeadline);
    }

    public void setDeadlines(List<Deadline> deadlines) {
        this.deadlines.setDeadlines(deadlines);
    }

    /**
     * Returns true if a deadline with the same identity as {@code deadline} exists in the address book.
     */
    public boolean hasDeadline(Deadline deadline) {
        requireNonNull(deadline);
        return deadlines.contains(deadline);
    }

    //@@author LeonardTay748
    public void addGood() {
        stats[0]++;
    }
    public void addHard() {
        stats[1]++;
    }
    public void addEasy() {
        stats[2]++;
    }

    public int[] getStats() {
        return stats;
    }

}
