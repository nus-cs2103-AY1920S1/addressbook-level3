package seedu.address.flashcard;

import java.util.HashSet;

/**
 * A class that represents each individual tagged flashcard set
 * Guarantees: every tag must have a unique name, no two tags can have the same name
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "The tag name can take in any value, but it should not be empty ";

    private HashSet<Flashcard> cardList;
    private String name;

    /**
     * Constructs a tag object.
     *
     * @param name the name of the tag to be initialized.
     */
    public Tag(String name) {
        this.name = name;
        cardList = new HashSet<>();
    }

    /**
     * Add a new flashcard to the tag.
     *
     * @param c the flashcard to be added.
     */
    public void addFlashcard(Flashcard c) {
        cardList.add(c);
    }

    /**
     * Give a list of all the flashcards under this tag.
     *
     * @return a list of all flashcards.
     */
    public HashSet<Flashcard> getFlashCards() {
        return cardList;
    }


    /**
     * Remove a particular flashcard from the tag based on the flashcard ID.
     *
     * @param id the ID of the flashcard to be removed.
     */
    public void deleteFlashcard(CardId id) {
        for (Flashcard item: cardList) {
            if (item.getId().getIdentityNumber() == id.getIdentityNumber()) { // there are two different getId() and ID stored as int
                cardList.remove(item);
                break;
            }
        }
    }

    /**
     * Inform the name of the tag.
     *
     * @return name of the tag.
     */
    public String getName() {
        return name;
    }

    /**
     * Edit the name of the tag to a new one.
     *
     * @param newName the new name of the tag.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Check whether the tag contains a particular flashcard.
     *
     * @param c the flashcard to be checked.
     * @return whether the flashcard exists.
     */
    public boolean contains(Flashcard c) {
        return cardList.contains(c);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Check if two tags are equivalent to each other.
     * Two tags are the same if and only if their names are the same.
     *
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
