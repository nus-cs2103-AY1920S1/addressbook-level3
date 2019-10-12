package seedu.flashcard.flashcardmodel;

import java.util.ArrayList;

import seedu.flashcard.flashcardmodel.exceptions.CardNotFoundException;
import seedu.flashcard.flashcardmodel.exceptions.DuplicateCardException;
import seedu.flashcard.flashcardmodel.flashcard.Flashcard;

/**
 * A class that represents each individual tagged flashcardmodel set
 * Guarantees: every tag must have a unique name, no two tags can have the same name
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "The tag name can take in any value, but it should not be empty ";

    private ArrayList<Flashcard> cardList;
    private String name;

    public Tag(String name) {
        this.name = name;
        cardList = new ArrayList<>();
    }

    /**
     * Add a new flashcardmodel to the tag
     * @param c the flashcardmodel to be added.
     * @throws DuplicateCardException if the card is already under this tag
     */
    public void addFlashcard(Flashcard c) throws DuplicateCardException {
        if (cardList.contains(c)) {
            throw new DuplicateCardException();
        }
        cardList.add(c);
    }

    /**
     * Get the list of all the flashcards under this tag.
     * @return a list of all flashcards.
     */
    public ArrayList<Flashcard> getFlashcards() {
        return cardList;
    }


    /**
     * Remove a particular flashcardmodel from the tag based on the flashcardmodel ID
     * @param id the ID of the flashcardmodel to be removed
     * @throws CardNotFoundException the card with this ID is not found under this tag
     */
    public void deleteFlashcard(int id) throws CardNotFoundException {
        boolean cardWasFound = false;
        for (Flashcard item : cardList) {
            if (item.getId().getIdentityNumber() == id) {
                cardList.remove(item);
                cardWasFound = true;
                break;
            }
        }
        if (!cardWasFound) {
            throw new CardNotFoundException();
        }
    }

    public String getName() {
        return name;
    }

    /**
     * Edit the name of the tag to a new one.
     * @param newName the new name of the tag.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * While searching for a tag, check whether the tag name contains the search keyword.
     * @param s The search keyword
     * @return true if the tag name contains the keyword, false otherwise
     */
    public boolean contains(String s) {
        return name.contains(s);
    }

    /**
     * Check whether the tag contains a particular flashcardmodel.
     * @param c the flashcardmodel to be checked.
     * @return whether the flashcardmodel exists.
     */
    public boolean hasCard(Flashcard c) {
        return cardList.contains(c);
    }

    /**
     * While deleting a tag from the {@Code TagManager}, the tag should at the same time being detached from all
     * of its flashcardmodel.
     */
    public void detachFromAllCards() {
        for (Flashcard card : cardList) {
            card.deleteTag(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Check if two tags are equivalent to each other.
     * Two tags are the same if and only if their names are the same.
     * @param other the other tag to be tested.
     * @return a boolean variable that informs whether the two tags are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Tag)) {
            return false;
        }
        Tag otherTag = (Tag) other;
        return otherTag.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
